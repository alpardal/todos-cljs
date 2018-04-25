(ns todos.views
  (:require [re-frame.core :refer [subscribe dispatch]]
            [todos.subs :as subs]
            [todos.events :as events]
            [todos.utils :refer [value-from-event preventing-default]]))

(defn render-todo [{:keys [id text complete?]}]
  (let [dom-id (str "todo-" id)
        label-class (when complete? "todo-item--complete")]
    [:li.todo-item {:key id}
     [:input.complete-todo-checkbox
      {:type "checkbox" :id dom-id :checked complete?
       :on-change #(dispatch [::events/toggle-todo id])}]
     [:label {:for dom-id :class label-class} text]
     [:button.remove-todo-button
      {:on-click #(dispatch [::events/remove-todo id])}
      "x"]]))

(defn todo-list []
  (let [todos @(subscribe [::subs/todos])]
    [:ul.todo-list (map render-todo todos)]))

(defn new-todo-form []
  (let [disabled? (not @(subscribe [::subs/valid?]))]
    [:form {:on-submit (preventing-default #(dispatch [::events/add-new-todo]))}
     [:input.new-todo-input
      {:placeholder "enter a new item..."
       :value @(subscribe [::subs/new-todo-text])
       :on-change #(dispatch [::events/edit-new-todo (value-from-event %)])}]
     [:button {:disabled disabled?} "Add"]]))

(defn todos-left-notice []
  (let [count @(subscribe [::subs/todos-left-count])
        inflected (if (= 1 count) "item" "items")
        text (if (zero? count) (str "No " inflected " left :)")
                               (str count " " inflected " left"))]
    [:div.todos-left-notice text]))

(defn main-panel []
  [:div
   [:h1 "TODOs"]
   [todo-list]
   [todos-left-notice]
   [new-todo-form]])
