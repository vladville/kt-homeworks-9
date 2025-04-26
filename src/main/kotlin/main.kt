package ru.netology;

import java.util.*

data class Chat(val messages: MutableList<Message> = mutableListOf())
data class Message(var id: Int = 0, var text: String, var read: Boolean = false)

object ChatService {
    private var chats = mutableMapOf<SortedSet<Int>, Chat>()
    private var lastId = 0

    fun getChats() = chats

    fun addMessage(userIds: SortedSet<Int>, message: Message) {
        message.id = ++lastId
        chats.getOrPut(userIds) { Chat() }.messages += message
    }

    fun getLastMessages() = chats.values.map { it.messages.lastOrNull()?.text ?: "Нет сообщений" }

    fun getMessagesFromChat(userIds: SortedSet<Int>, count: Int = 0): List<Message> {
        val chat = chats.filter { e -> e.key.containsAll(userIds) }.values.first().messages
        if (count > 0) {
            var chatNew = chat.slice(0..count - 1)
            chatNew.forEach { message -> message.read = true }
            return chatNew
        }
        chat.forEach { message -> message.read = true }
        return chat
    }

    fun removeChat(userIds: SortedSet<Int>) = chats.remove(userIds)

    fun removeMessageFromChat(userIds: SortedSet<Int>, id: Int) {
        val chat = chats.filter { e -> e.key.containsAll(userIds) }.values.first().messages
        val iterator = chat.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.id == id) {
                iterator.remove()
            }
        }
    }

    fun getUnreadChatsCount() = chats.values.count() { chat: Chat -> chat.messages.any { !it.read } }

    fun clear() {
        chats = mutableMapOf<SortedSet<Int>, Chat>()
        lastId = 0
    }
}

fun main() {

    ChatService.addMessage(sortedSetOf(1, 2), Message(text = "1ое Тестовое сообщение между 1 и 2"))
    ChatService.addMessage(sortedSetOf(1, 2), Message(text = "2ое Тестовое сообщение между 1 и 2"))
    ChatService.addMessage(sortedSetOf(1, 3), Message(text = "Тестовое сообщение между 1 и 3"))
    ChatService.addMessage(sortedSetOf(3, 4), Message(text = "Тестовое сообщение между 3 и 4"))
    ChatService.addMessage(sortedSetOf(1, 2), Message(text = "2ое Тестовое сообщение между 1 и 2"))

    //все чаты
    println(ChatService.getChats())

    //тест чтения сообщений
    println(ChatService.getMessagesFromChat(sortedSetOf(1, 2), 1))
    println(ChatService.getMessagesFromChat(sortedSetOf(1, 3)))

    //проверяем непрочитанные чатов == 1
    println(ChatService.getUnreadChatsCount())

    //тест удаления чата
    ChatService.removeChat(sortedSetOf(1, 3))
    println(ChatService.getChats())

    //проверяем получение последних сообщений всех чатов
    println(ChatService.getLastMessages())

    //удаление сообщения
    ChatService.removeMessageFromChat(sortedSetOf(1, 2), 2)
    println(ChatService.getMessagesFromChat(sortedSetOf(1, 2)))
}