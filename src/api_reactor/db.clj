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
    (jdbc/query db [query-str])))

(defn get-entity
  "TODO"
  [db table p-key &
   {:keys [p-key-column]
    :or   {p-key-column (:default-p-key db)}}]
  (let [query-str (str "select * from " table
                       " where " p-key-column " = ?")]
    (first (jdbc/query db [query-str p-key]))))

(defn create-entity!
  "TODO"
  [db table & entities-and-columns]
  (apply jdbc/insert! db (symbol table) entities-and-columns))

(defn update-entity!
  "TODO"
  [db table entity p-key &
   {:keys [p-key-column]
    :or   {p-key-column (:default-p-key db)}}]
  (let [where-str (str p-key-column " = ?")
        table-sym (symbol table)]
    (jdbc/update! db table-sym entity
                  [where-str p-key])))

(defn delete-entity!
  "TODO"
  [db table p-key &
   {:keys [p-key-column]
    :or   {p-key-column (:default-p-key db)}}]
  (let [where-str (str p-key-column " = ?")
        table-sym (symbol table)]
    (jdbc/delete! db table-sym
                  [where-str p-key])))
