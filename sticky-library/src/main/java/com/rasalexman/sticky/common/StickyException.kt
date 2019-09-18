package com.rasalexman.sticky.common

/**
 *
 */
sealed class StickyException(message: String?) : RuntimeException(message) {

    /**
     *
     */
    class StickyCastException : StickyException("Base Fragment does not implement interface IStickyView")

    /**
     *
     */
    class ReceiverClassException : StickyException("Critical Exception. Receiver class does not exist, or null")

    /**
     *
     */
    class ExecutionBlockException : StickyException("Execution block does not exist or does not initialized")

    /**
     *
     */
    class UncheckedException(message: String? = "There is a Failure Result in Sticky::resumeWith") : StickyException(message)
}