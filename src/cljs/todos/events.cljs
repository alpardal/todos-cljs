(ns todos.events
  (:require [re-frame.core
             :refer
             [reg-event-db reg-event-fx reg-fx reg-cofx inject-cofx dispatch]]
            [todos.db :as db]
            [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))


(reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))
