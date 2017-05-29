(ns com.degel.re-frame.storage
  (:require ;; [clojure.browser.repl :as repl]
            [re-frame.core :refer [console dispatch reg-cofx reg-fx]]))

;; (defonce conn
;;   (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)


;; The Web Storage API offers the ability to store data on the client,
;; per-session or persistently, and is supported by nearly all modern browsers.
;; Unlike cookies, web storage is used purely on the client.

(defn storage-area [session?]
  (aget js/window
        (if session? "sessionStorage" "localStorage")))


;; A coeffect handler that injects whether there are any stored values for this
;; document or session.

(reg-cofx
 :storage/empty?
 (fn [coeffects {:keys [session?]}]
   (assoc coeffects :storage/empty?
          (= 0 (.-length (storage-area session?))))))


;; A coeffect handler that injects the stored value(s) associated with the given
;; name(s).

(reg-cofx
 :storage/get
 (fn [coeffects {:keys [session? names name]}]
   (assoc coeffects :storage/get
          (let [storage (storage-area session?)]
            (if names
              (reduce (fn [acc name]
                        (into acc {name (.getItem storage name)}))
                      {} names)
              (.getItem storage name))))))


;; Ceoeffect handlers to inject all the key/value pairs or just a seq of
;; the keys or values. (The latter mostly for compatibility with
;; com.smxemail/re-frame-cookie-fx).

(defn- get-all-pairs [session?]
  (let [storage (storage-area session?)]
    (into {}
          (for [n (range (.-length storage))]
            (let [name (.key storage n)]
              {name (.getItem storage name)})))))

(reg-cofx
 :storage/all
 (fn [coeffects {:keys [session?]}]
   (assoc coeffects :storage/all
          (get-all-pairs session?))))

(reg-cofx
 :storage/keys
 (fn [coeffects {:keys [session?]}]
   (assoc coeffects :storage/keys
          (keys (get-all-pairs session?)))))

(reg-cofx
 :storage/vals
 (fn [coeffects {:keys [session?]}]
   (assoc coeffects :storage/keys
          (vals (get-all-pairs session?)))))


;; A coeffect handler that injects the number of stored values.

(reg-cofx
 :storage/count
 (fn [coeffects {:keys [session?]}]
   (assoc coeffects :storage/count
          (.-length (storage-area session?)))))


;; An effects handler that store one or more items in local or session storage.
;; To set a value, supply:
;;   :session? True to store in session storage, or defaults to local
;;             persistent storage.
;;   :pairs    Sequence of :name, :value maps.
;;   :name     Key of the item (will be stored as a string).
;;   :value    Value of the item (will be stored as a string).

(defn- set-one-item [storage name value]
  (.setItem storage (str name) (str value)))

(reg-fx
 :storage/set
 (fn storage-set-effect [{:keys [session? pairs name value]}]
   (let [storage (storage-area session?)]
     (if pairs
       (run! (fn [{:keys [name value]}]
               (set-one-item storage name value))
             pairs)
       (set-one-item storage name value)))))


;; An effects handler that removes one or more items from local or session
;; storage.

(reg-fx
 :storage/remove
 (fn storage-remove-effect [{:keys [session? names name]}]
   (let [storage (storage-area session?)]
     (if names
       (run! #(.removeItem storage %) names)
       (.removeItem storage name)))))
