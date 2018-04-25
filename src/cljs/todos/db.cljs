(ns todos.db)


(def default-db
  {:todos
   {1 {:id 1 :text "first item" :complete? true}
    2 {:id 2 :text "second item" :complete? false}}
   :todo-list [1 2]
   :new-todo-form {:text ""}})
