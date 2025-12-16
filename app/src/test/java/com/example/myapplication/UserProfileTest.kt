package com.example.myapplication

import com.example.myapplication.data.models.UserProfile
import org.junit.Test
import org.junit.Assert.*

class UserProfileTest {
    
    @Test
    fun userProfile_creation_isCorrect() {
        val userProfile = UserProfile(
            id = "test_001",
            name = "Test User",
            email = "test@example.com",
            status = "Online"
        )
        
        assertEquals("test_001", userProfile.id)
        assertEquals("Test User", userProfile.name)
        assertEquals("test@example.com", userProfile.email)
        assertEquals("Online", userProfile.status)
    }
}