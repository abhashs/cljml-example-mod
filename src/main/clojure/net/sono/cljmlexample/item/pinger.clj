(ns net.sono.cljmlexample.item.pinger
  (:require [net.sono.cljml.item :refer [defblockitem defitem]]
            [net.sono.cljmlexample.block.pinger :refer [pinger-block]])
  (:import (net.minecraft.world.item Item$Properties)))

(defblockitem pinger-block-item pinger-block)

(defitem simple-item
  :properties (-> (Item$Properties.)
                  (.stacksTo 16)))
