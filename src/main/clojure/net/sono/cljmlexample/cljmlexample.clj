(ns net.sono.cljmlexample.cljmlexample
  (:require [net.sono.cljml.util :refer [start-repl!]])
  (:import (net.minecraft.core.registries Registries)
           (net.minecraft.resources ResourceKey)
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
  ;(.register BLOCKS "test-block" (:register-fn extractor/test-block))
  )

(defn register-block-entity-types []
  )

(defn register-items []
  )

(defn -init [^IEventBus mod-bus]
  (println "Clojure mod" MOD-ID "initialized!")

  ; Start a socket REPL server for remote interaction
  (start-repl!))