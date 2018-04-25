(ns todos.events
  (:require [re-frame.core
             :refer
             [reg-event-db reg-event-fx reg-fx reg-cofx inject-cofx dispatch]]
            [todos.db :as db]
            [todos.utils :refer [safe-inc]]
            [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))


(defn next-id [db]
  (safe-inc (apply max (:todo-list db))))

(defn-traced edit-new-todo [db [_ text]]
  (assoc-in db [:new-todo-form :text] text))

(defn-traced add-new-todo [db _]
  (let [id (next-id db)
        text (get-in db [:new-todo-form :text])
        new-todo {:id id :text text}]
    (-> db
      (assoc-in [:todos id] new-todo)           ;; adiciona todo novo
      (update :todo-list #(conj % id))          ;; adiciona id do novo todo à lista de ids
      (assoc-in [:new-todo-form :text] ""))))   ;; limpa form

(reg-event-db ::initialize-db
 (fn-traced [_ _] db/default-db))
(reg-event-db ::edit-new-todo edit-new-todo)
(reg-event-db ::add-new-todo add-new-todo)
