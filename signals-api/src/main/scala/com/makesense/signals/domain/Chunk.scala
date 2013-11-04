package com.makesense.signals.domain

import

case class Chunk(
  val id: Option[String],
  val word: Option[String],
  val context: Option[String],
  val tags: Set[String],
  val createdAt: DateTime

                  )
