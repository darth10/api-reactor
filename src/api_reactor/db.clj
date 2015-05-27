(ns api-reactor.db
  (:import [java.sql SQLException])
  (:require [clojure.java.jdbc :as jdbc]))

(def mysql-db {:subprotocol "mysql"
               :subname "//127.0.0.1:3306/slimdb"
               :user "phinxuser"
               :password "password"
               :default-p-key "id"})

;;; TODO doc strings

(defn get-entity-list
  "TODO"
  [db table & opts]
  (let [opt-str ""                      ; TODO
        query-str (str "select * from " table
                       " " opt-str)]
    (try
      (jdbc/query db [query-str])
      (catch SQLException se se))))

(defn get-entity
  "TODO"
  [db table p-key &
   {:keys [p-key-column]
    :or   {p-key-column (:default-p-key db)}}]
  (let [query-str (str "select * from " table
                       " where " p-key-column " = ?")]
    (try
      (first (jdbc/query db [query-str p-key]))
      (catch SQLException se se))))

(defn create-entity!
  "TODO"
  [db table & entities-and-columns]
  (try
    (apply jdbc/insert! db (symbol table) entities-and-columns)
    (catch SQLException se se)))

(defn update-entity!
  "TODO"
  [db table entity p-key &
   {:keys [p-key-column]
    :or   {p-key-column (:default-p-key db)}}]
  (let [where-str (str p-key-column " = ?")
        table-sym (symbol table)]
    (try
      (jdbc/update! db table-sym entity
                    [where-str p-key])
      (catch SQLException se se))))

(defn delete-entity!
  "TODO"
  [db table p-key &
   {:keys [p-key-column]
    :or   {p-key-column (:default-p-key db)}}]
  (let [where-str (str p-key-column " = ?")
        table-sym (symbol table)]
    (try
      (jdbc/delete! db table-sym
                    [where-str p-key])
      (catch SQLException se se))))
