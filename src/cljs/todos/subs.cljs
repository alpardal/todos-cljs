(ns todos.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]))


(reg-sub
 ::todos
 (fn [db]
   (let [todo-by-id #(get-in db [:todos %])]
     (map todo-by-id (:todo-list db)))))

(reg-sub
  ::new-todo-text
  #(get-in % [:new-todo-form :text]))
