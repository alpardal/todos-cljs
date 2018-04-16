(ns todos.views
  (:require [re-frame.core :refer [subscribe dispatch]]
            [todos.subs :as subs]
            [todos.events :as events]))


(defn main-panel []
  [:div
   [:h1 "Hello!"]])
