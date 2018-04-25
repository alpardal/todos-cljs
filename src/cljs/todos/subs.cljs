(ns todos.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]))


(reg-sub
 ::todos
 (fn [db]
   (let [todo-by-id #(get-in db [:todos %])]
     (map todo-by-id (:todo-list db)))))
