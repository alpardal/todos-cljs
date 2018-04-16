(ns todos.events
  (:require [re-frame.core
             :refer
             [reg-event-db reg-event-fx reg-fx reg-cofx inject-cofx dispatch]]
            [todos.db :as db]))


(reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
