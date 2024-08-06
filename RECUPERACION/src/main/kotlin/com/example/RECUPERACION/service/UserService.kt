
import com.example.RECUPERACION.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: Long): User = userRepository.findById(id)
        .orElseThrow { NoSuchElementException("User with ID $id not found") }

    fun createUser(user: User): User = userRepository.save(user)

    @Transactional
    fun updateUser(id: Long, updatedUser: User): User {
        val existingUser = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with ID $id not found") }

        // Actualiza los campos del usuario existente
        existingUser.username = updatedUser.username
        existingUser.email = updatedUser.email
        existingUser.password = updatedUser.password

        // Guarda el usuario actualizado
        return userRepository.save(existingUser)
    }

    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw NoSuchElementException("User with ID $id not found")
        }
        userRepository.deleteById(id)
    }

    fun partialUpdateUser(id: Long, updates: Map<String, Any>): User {
        val existingUser = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with ID $id not found") }

        // Actualiza solo los campos proporcionados en el mapa
        updates["username"]?.let { existingUser.username = it.toString() }
        updates["email"]?.let { existingUser.email = it.toString() }
        updates["password"]?.let { existingUser.password = it.toString() }

        // Guarda el usuario actualizado
        return userRepository.save(existingUser)
    }
}
