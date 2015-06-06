(ns api-reactor.middleware
  (:import [java.sql SQLException])
  (:require [clojure.tools.logging :as log]
            [api-reactor.config :as conf]))

(defmulti handle-api-request-method
  (fn [req uname pwd]
    (:request-method req)))

(defmethod handle-api-request-method :get
  [req uname pwd]
  ;; TODO
  (println "get")
  )

(defmethod handle-api-request-method :post
  [req uname pwd]
  ;; TODO
  (println "put")
  )

(defmethod handle-api-request-method :put
  [req]
  ;; TODO
  )

(defmethod handle-api-request-method :delete
  [req]
  ;; TODO
  )

(defn get-error-response [req sql-ex]
  ;; TODO
  )

(defn handle-api-request [req uname pwd]
  (try
    (handle-api-request-method req uname pwd)
    (catch SQLException se
      (get-error-response req se))))

(defn get-credentials [req]
  (let [auth-header ((:headers req) "authorization")]
    ;; TODO
    ["user" "pwd"]))

(defn api-reactor-middleware [req]
  (let [req-path    (:uri req)
        req-method  (:request-method req)
        [uname pwd] (get-credentials req)
        ]
    (log/info (str (:request-method req) " -> " (:uri req)))
    {:status 200
     :body "Hi!"}))                     ; TODO
