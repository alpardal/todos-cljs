(ns todos.utils)

(defn value-from-event [e]
  (.. e -target -value))

(def safe-inc (fnil inc 0))

(defn preventing-default [f]
  #(-> % (.preventDefault) (f)))
