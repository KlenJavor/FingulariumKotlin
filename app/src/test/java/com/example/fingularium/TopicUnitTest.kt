package com.example.fingularium

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import com.example.fingularium.data.TopicsSingleton.topics
import com.example.fingularium.model.Result
import com.example.fingularium.model.Topic
import org.junit.Assert.assertEquals
import org.junit.Test

class TopicUnitTests {

    @Test
    fun createResult() {
        val r = Topic("Uudenvuodenpuhe", 1919, 15, "President", "Tasavallan", "uudenvuodenpuhe")
        assertEquals(r.getTitle(), "UUDENVUODENPUHE")
        assertEquals(r.getLenght().toInt(), 1919)
        assertEquals(r.getChapters().toInt(), 15)
        assertEquals(r.enName, "President")
        assertEquals(r.fiName, "Tasavallan")
        assertEquals(r.audio, "uudenvuodenpuhe")
        assertEquals(r.toString(), "Uudenvuodenpuhe")
    }

    @Test
    fun topicsIdentical() {
        val r = Topic("Uudenvuodenpuhe", 1919, 15, "President", "Tasavallan", "uudenvuodenpuhe")
        val s = Topic("Uudenvuodenpuhe", 1919, 15, "President", "Tasavallan", "uudenvuodenpuhe")
        topics.add(r)
        topics.add(s)
        assertEquals(false, topics[0].equals(topics[1]))
    }

    @Test
    fun getTopic() {
        val i = 3
        val r = Topic("Uudenvuodenpuhe", 1919, 15, "President", "Tasavallan", "uudenvuodenpuhe")
        val s = Topic("Vanhavuodenpuhe", 1919, 15, "President", "Tasavallan", "uudenvuodenpuhe")
        topics.add(r)
        topics.add(s)
        assertEquals("MAAMME", topics.get(1).getTitle())
        assertEquals(topics[i], topics.get(i))
        assertEquals(topics.get(i), topics[i])
    }
}