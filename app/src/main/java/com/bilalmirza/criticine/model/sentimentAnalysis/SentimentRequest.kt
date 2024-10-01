package com.bilalmirza.criticine.model.sentimentAnalysis

data class SentimentRequest(val document: Document, val encodingType: String = "UTF8")