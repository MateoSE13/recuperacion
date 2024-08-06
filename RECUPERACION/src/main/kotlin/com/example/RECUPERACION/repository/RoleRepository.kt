package com.example.RECUPERACION.repository

import com.example.RECUPERACION.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>
