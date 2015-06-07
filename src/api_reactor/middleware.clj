(ns api-reactor.middleware
  (:import [java.sql SQLException])
  (:require [clojure.tools.logging :as log]
            [clojure.string :as string]
            [api-reactor.config :as conf]
            [api-reactor.db :as db]))

(defn- http-ok [& {:keys [body]
                   :or   {body nil}}]
  {:status 200
   :body body})

(defn- http-not-found []
  {:status 404})

(defn- http-not-allowed []
  {:status 405})

(defn- http-server-error []
  {:status 500})

(defmulti handle-api-request-method
  (fn [req db-conf]
    (:request-method req)))

(defn handle-api-get-list [db-conf table]
  (if (= (count table) 0)
    (http-not-found)                    ; for '/' route
    (http-ok :body (db/get-entity-list db-conf table))))

(defn handle-api-get [db-conf table p-key]
  (let [entity (db/get-entity db-conf table p-key)]
    (if (nil? entity)
      (http-not-found)
      (http-ok :body entity))))

(defmethod handle-api-request-method :get
  [req db-conf]
  (let [req-uri (:uri req)
        uri-parts (string/split (subs req-uri 1) #"/")
        uri-parts-count (count uri-parts)]
    (cond
      (= uri-parts-count 1) (handle-api-get-list
                             db-conf
                             (uri-parts 0))
      (= uri-parts-count 2) (handle-api-get
                             db-conf
                             (uri-parts 0)
                             (uri-parts 1))
      :else (http-not-allowed))))

(defmethod handle-api-request-method :post
  [req db-conf]
  ;; TODO
  )

(defmethod handle-api-request-method :put
  [req db-conf]
  ;; TODO
  )

(defmethod handle-api-request-method :delete
  [req db-conf]
  ;; TODO
  )

(defn get-error-response [req sql-ex]
  ;; TODO
  (let [err-code (.getErrorCode sql-ex)]
    (cond                               ; TODO change to condp
      (= err-code 1146) (http-not-found)
      :else (http-server-error))))

(defn handle-api-request [req uname pwd]
  (let [db-conf (conf/get-db-config)]
    ;; TODO merge with uname and pwd
    (try
      (handle-api-request-method req db-conf)
      (catch SQLException se
        (log/error (str "JDBC error: " (.getErrorCode se)))  ; TODO debug only
        (get-error-response req se)))))

(defn get-credentials [req]
  (let [auth-header ((:headers req) "authorization")]
    ;; TODO
    ["uname" "pwd"]))

;;; TODO convert to middleware with options
(defn api-reactor-middleware [req]
  (let [req-path    (:uri req)
        req-method  (:request-method req)
        [uname pwd] (get-credentials req)]
    (log/info (str (:request-method req) " -> " (:uri req)))
    (handle-api-request req uname pwd)))
