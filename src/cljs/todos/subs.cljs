(ns todos.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]))


(reg-sub
 ::name
 (fn [db]
   (:name db)))
