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

(defn-traced add-new-todo [{:keys [db now]} _]
  (let [id (next-id db)
        text (get-in db [:new-todo-form :text])
        new-todo {:id id :text text :created-at now}]
    {:db (-> db
          (assoc-in [:todos id] new-todo)            ;; adiciona todo novo
          (update :todo-list #(conj % id))           ;; adiciona id do novo todo à lista de ids
          (assoc-in [:new-todo-form :text] ""))}))   ;; limpa form

(defn remove-todo [_ [_ id msg]]
  {::with-confirmation
   {:msg msg :on-confirm [::remove-todo-without-confirmation id]}})

(defn-traced remove-todo-without-confirmation [db [_ id]]
  (-> db
    (update :todos #(dissoc % id))                          ;; remove todo
    (update :todo-list #(vec (remove (partial = id) %)))))  ;; remove id da lista

(defn-traced toggle-todo [db [_ id]]
  (update-in db [:todos id :complete?] not))

(reg-fx
  ::with-confirmation
  (fn [{:keys [msg on-confirm]}]
    (when (.confirm js/window msg) (dispatch on-confirm))))

(reg-cofx
  ::now
  (fn [cofx] (assoc cofx :now (new js/Date))))

(reg-event-db ::initialize-db
 (fn-traced [_ _] db/default-db))
(reg-event-db ::edit-new-todo edit-new-todo)
(reg-event-fx ::add-new-todo [(inject-cofx ::now)] add-new-todo)
(reg-event-fx ::remove-todo remove-todo)
(reg-event-db ::remove-todo-without-confirmation remove-todo-without-confirmation)
(reg-event-db ::toggle-todo toggle-todo)
