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

(reg-sub
  ::valid?
  #(subscribe [::new-todo-text])  ;; `::valid?` depende do texto do todo novo
  (comp not empty?))              ;; só é válido quando o texto não estiver em branco
