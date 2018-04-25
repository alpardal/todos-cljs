(ns todos.views
  (:require [re-frame.core :refer [subscribe dispatch]]
            [todos.subs :as subs]
            [todos.events :as events]
            [todos.utils :refer [value-from-event preventing-default]]))

(defn render-todo [{:keys [id text]}]
  (let [dom-id (str "todo-" id)]
    [:li.todo-item {:key id}
     [:input.complete-todo-checkbox {:type "checkbox" :id dom-id}]
     [:label {:for dom-id} text]
     [:button.remove-todo-button "x"]]))

(defn todo-list []
  (let [todos @(subscribe [::subs/todos])]
    [:ul.todo-list (map render-todo todos)]))

(defn new-todo-form []
  [:form {:on-submit (preventing-default #(dispatch [::events/add-new-todo]))}
   [:input.new-todo-input
    {:placeholder "enter a new item..."
     :value @(subscribe [::subs/new-todo-text])
     :on-change #(dispatch [::events/edit-new-todo (value-from-event %)])}]
   [:button "Add"]])

(defn main-panel []
  [:div
   [:h1 "TODOs"]
   [todo-list]
   [new-todo-form]])
