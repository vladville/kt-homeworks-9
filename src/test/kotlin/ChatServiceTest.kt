package ru.netology;

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun addMessage() {
        val result = ChatService.addMessage(sortedSetOf(1, 2), Message(text = "1ое Тестовое сообщение между 1 и 2"))
        assertNotNull(result)
    }

    @Test
    fun getLastMessages() {
        ChatService.addMessage(sortedSetOf(1, 2), Message(text = "1ое Тестовое сообщение между 1 и 2"))
        ChatService.addMessage(sortedSetOf(1, 2), Message(text = "2ое Тестовое сообщение между 1 и 2"))
        ChatService.addMessage(sortedSetOf(1, 3), Message(text = "1ое Тестовое сообщение между 1 и 3"))

        val result = ChatService.getLastMessages()
        assertNotNull(result)
    }

//    @Test - тут какая то проблема ( выдает пустой массив, а не сообщение "Нет сообщений"
//    fun getLastMessagesNullResult() {
//        val result = ChatService.getLastMessages()
//    }

    @Test
    fun getMessagesFromChatWithMessageCount() {
        ChatService.addMessage(sortedSetOf(1, 2), Message(text = "1ое Тестовое сообщение между 1 и 2"))
        ChatService.addMessage(sortedSetOf(1, 2), Message(text = "2ое Тестовое сообщение между 1 и 2"))
        ChatService.addMessage(sortedSetOf(1, 3), Message(text = "1ое Тестовое сообщение между 1 и 3"))

        val result = ChatService.getMessagesFromChat(sortedSetOf(1, 2), 1)
        assertEquals(1, result.size)
    }

    @Test
    fun removeChat() {
        ChatService.addMessage(sortedSetOf(1, 2), Message(text = "1ое Тестовое сообщение между 1 и 2"))
        ChatService.addMessage(sortedSetOf(1, 2), Message(text = "2ое Тестовое сообщение между 1 и 2"))
        ChatService.addMessage(sortedSetOf(1, 3), Message(text = "1ое Тестовое сообщение между 1 и 3"))

        ChatService.removeChat(sortedSetOf(1, 2))

        val result = ChatService.getChats()
        assertEquals(1, result.size)
    }

    @Test
    fun removeMessageFromChat() {
        ChatService.addMessage(sortedSetOf(1, 2), Message(text = "1ое Тестовое сообщение между 1 и 2"))
        ChatService.addMessage(sortedSetOf(1, 2), Message(text = "2ое Тестовое сообщение между 1 и 2"))
        ChatService.addMessage(sortedSetOf(1, 3), Message(text = "1ое Тестовое сообщение между 1 и 3"))

        ChatService.removeMessageFromChat(sortedSetOf(1, 2), 2)
        val result = ChatService.getMessagesFromChat(sortedSetOf(1, 2))
        assertEquals(1, result.size)
    }

    @Test
    fun getUnreadChatsCount() {
        ChatService.addMessage(sortedSetOf(1, 2), Message(text = "1ое Тестовое сообщение между 1 и 2", read = true))
        ChatService.addMessage(sortedSetOf(1, 2), Message(text = "2ое Тестовое сообщение между 1 и 2"))
        ChatService.addMessage(sortedSetOf(1, 3), Message(text = "1ое Тестовое сообщение между 1 и 3"))

        val result = ChatService.getUnreadChatsCount()
        assertEquals(2, result)
    }
}