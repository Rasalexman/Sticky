// Copyright (c) 2019 Aleksandr Minkin (sphc@yandex.ru)
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software
// and associated documentation files (the "Software"), to deal in the Software without restriction,
// including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
// subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
// IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
// THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.rasalexman.sticky.common

/**
 * Stickies exception base class
 */
sealed class StickyException(message: String?) : RuntimeException(message) {

    /**
     * When your base Fragment does not implement [IStickyView]
     */
    class StickyCastException : StickyException("Base Fragment does not implement interface IStickyView")

    /**
     *  Critical Error
     */
    class ReceiverClassException : StickyException("Critical Exception. Receiver class does not exist, or null")

    /**
     * Exception with [StickyBlock]
     */
    class ExecutionBlockException : StickyException("Execution block does not exist or does not initialized")

    /**
     * Another error exception
     */
    class UncheckedException(message: String? = "There is a Failure Result in Sticky::resumeWith") : StickyException(message)
}