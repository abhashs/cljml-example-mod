(ns net.sono.cljmlexample.cljmlexample
  (:require [net.sono.cljml.util :refer [start-repl!]]
            [net.sono.cljmlexample.block.pinger :refer [pinger-block]]
            [net.sono.cljmlexample.item.pinger :refer [pinger-block-item simple-item]])
  (:import (net.minecraft.core.registries Registries)
           (net.minecraft.network.chat Component)
           (net.minecraft.resources ResourceKey)
           (net.minecraft.world.item CreativeModeTab)
           (net.neoforged.bus.api IEventBus)
           (net.neoforged.fml.common Mod)
           (net.neoforged.neoforge.registries DeferredRegister)))

(gen-class
  :name ^{Mod "cljmlexamplemod"} net.sono.cljmlexample.cljmlexample
  :init init
  :constructors {[net.neoforged.bus.api.IEventBus] []})

(def ^:const MOD-ID "cljmlexamplemod")

(def BLOCKS
  (DeferredRegister/createBlocks MOD-ID))
(def ITEMS
  (DeferredRegister/createItems MOD-ID))
(def BLOCK-ENTITY-TYPES
  (^[ResourceKey String] DeferredRegister/create Registries/BLOCK_ENTITY_TYPE MOD-ID))
(def CREATIVE-MODE-TABS
  (^[ResourceKey String] DeferredRegister/create Registries/CREATIVE_MODE_TAB MOD-ID))

(defn register-blocks []
  (.register BLOCKS "pinger" (:register-fn pinger-block)))

(defn register-block-entity-types [] )

(defn register-items []
  (.register ITEMS "pinger" (:register-fn pinger-block-item))
  (.register ITEMS "simple_item" (:register-fn simple-item)))

(defn register-creative-tabs []
  (.register CREATIVE-MODE-TABS "example-tab"
             (fn []
               (-> (CreativeModeTab/builder)
                   (.title (Component/translatable (str "itemgroup." MOD-ID)))
                   (.displayItems
                     (fn [params output]
                       (.accept output @(:ref pinger-block-item))
                       (.accept output @(:ref simple-item))
                       ))
                   (.build)))))

(defn -init [^IEventBus mod-bus]
  (println "Clojure mod" MOD-ID "initialized!")

  (register-items)
  (register-blocks)
  ;(register-block-entity-types)
  (register-creative-tabs)

  ; Start a socket REPL server for remote interaction
  (start-repl!)

  (.register BLOCKS mod-bus)
  (.register ITEMS mod-bus)
  (.register BLOCK-ENTITY-TYPES mod-bus)
  (.register CREATIVE-MODE-TABS mod-bus))