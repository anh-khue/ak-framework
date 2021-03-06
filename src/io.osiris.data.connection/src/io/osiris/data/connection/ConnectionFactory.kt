@file:JvmName("ConnectionFactory")

package io.osiris.data.connection

import io.osiris.data.connection.properties.PropertiesConnection
import io.osiris.data.connection.properties.PropertiesReader
import io.osiris.data.connection.xml.XmlConnection
import io.osiris.data.connection.xml.XmlReader
import java.io.File
import java.sql.Connection
import java.sql.SQLException

class ConnectionFactory : ConnectionAdapter {
    
    override fun databaseName(): String {
        val url = ClassLoader::class.java.getResource("resources/connection.properties")
        return if (url != null) {
            PropertiesReader.name
        } else {
            XmlReader.name
        }
    }
    
    @Throws(ClassNotFoundException::class, SQLException::class)
    override fun openConnection(): Connection {
        
        val file = File("resources/connection.properties")
        println(file.absolutePath)
        return if (file.exists()) {
            PropertiesConnection.openConnection()
        } else {
            XmlConnection.openConnection()
        }
    }
}
