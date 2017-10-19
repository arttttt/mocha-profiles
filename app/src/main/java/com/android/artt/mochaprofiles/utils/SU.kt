package com.android.artt.mochaprofiles.utils

import com.android.artt.mochaprofiles.extensions.emptyString
import com.android.artt.mochaprofiles.extensions.space
import java.io.*

class SU {
    companion object {
        val instance by lazy { SU() }
    }
    private lateinit var mProcess: Process
    private lateinit var mWriter: BufferedWriter
    private var denied: Boolean = false

    fun getSuAccess(): Boolean {
        try {
            mProcess = Runtime.getRuntime().exec("su")
            mWriter = BufferedWriter(OutputStreamWriter(mProcess.outputStream))
        } catch (e: IOException) {
            denied = true
        }

        return !denied
    }

    private fun openFile(file: String, permissions: String) {
        chmod(file, "0$permissions")
    }

    private fun closeFile(file: String, permissions: String) {
        chmod(file, "0$permissions")
    }

    fun readFile(file: String): String {
        try {
            val sbuilder = StringBuilder()

            BufferedReader(FileReader(File(file))).forEachLine {
                sbuilder.append("$it\n")
            }

            return sbuilder.toString()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return String.emptyString
    }

    fun writeFile(file: String, value: Any) {
        val filePermissions = Permissions.instance.getFilePermissions(file)
        openFile(file, Permissions.instance.getPermission(Permissions.ownerRead, Permissions.ownerWrite,
                Permissions.ownerRun, Permissions.groupRead, Permissions.groupWrite, Permissions.groupRun,
                Permissions.otherRead, Permissions.otherWrite, Permissions.otherRun))
        runCommand("echo '$value' >> $file")
        closeFile(file, filePermissions)
    }

    private fun chmod(file: String, permission: String) {
        runCommand("chmod $permission $file")
    }

    fun rootAccess(): Boolean {
        runCommand("echo /testRoot/")
        return !denied
    }

    private fun runCommand(command: String) {
        try {
            if (!denied) {
                mWriter.apply {
                    write("$command\necho /shellCallback/\n")
                    flush()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            denied = true
        }
    }

    fun close() {
        try {
            mWriter.apply {
                write("exit\n")
                flush()
                close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            mProcess.waitFor()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        mProcess.destroy()
    }

    private class Permissions {
        companion object {
            val ownerRead = 400
            val ownerWrite = 200
            val ownerRun = 100

            val groupRead = 40
            val groupWrite = 20
            val groupRun = 10

            val otherRead = 4
            val otherWrite = 2
            val otherRun = 1

            val instance: Permissions by lazy { Permissions() }
        }

        val OWNER = "owner"
        val GROUP = "group"
        val OTHER = "other"
        val NULL_PERMISSION = '-'

        private fun convertPermissions(permissionsMap: Map<String, String>): String {
            var permissions = 0
            permissionsMap.forEach { group, params ->
                when(group) {
                    OWNER -> {
                        (0 until params.length)
                                .asSequence()
                                .filter { params[it] != NULL_PERMISSION }
                                .forEach {
                                    permissions += when (it) {
                                        0 -> ownerRead
                                        1 -> ownerWrite
                                        2 -> ownerRun
                                        else -> 0
                                    }
                                }
                    }
                    GROUP -> {
                        (0 until params.length)
                                .asSequence()
                                .filter { params[it] != NULL_PERMISSION }
                                .forEach {
                                    permissions += when (it) {
                                        0 -> groupRead
                                        1 -> groupWrite
                                        2 -> groupRun
                                        else -> 0
                                    }
                                }
                    }
                    OTHER -> {
                        (0 until params.length)
                                .asSequence()
                                .filter { params[it] != NULL_PERMISSION }
                                .forEach {
                                    permissions += when (it) {
                                        0 -> otherRead
                                        1 -> otherWrite
                                        2 -> otherRun
                                        else -> 0
                                    }
                                }
                    }
                }
            }
            if (permissions < 10)
                return "00$permissions"

            if (permissions < 100)
                return "0$permissions"

            return permissions.toString()
        }

        fun getFilePermissions(file: String): String {
            var filePermissions = String.emptyString
            Runtime.getRuntime().exec("ls -l $file").apply {
                BufferedReader(InputStreamReader(inputStream)).apply {
                    forEachLine {
                        it.split(String.space)[0].let {
                            filePermissions = convertPermissions(mapOf(OWNER to it.substring(1, 4),
                                    GROUP to it.substring(4, 7),
                                    OTHER to it.substring(7, 10)))
                        }
                    }

                    close()
                }
                destroy()
            }
            return filePermissions
        }

        fun getPermission(vararg permissions: Int): String {
            var permission = 0
            permissions.forEach {
                permission += it
            }

            return permission.toString()
        }
    }
}