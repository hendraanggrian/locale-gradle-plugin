@file:Suppress("UnstableApiUsage")

package com.hendraanggrian.lokkal

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.logging.Logger
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.property
import java.io.File
import java.util.Locale

/** Gradle extension to configure localization, any changes made here will take affect in [AbstractLocalizeTask]. */
open class LokkalExtension(private val project: Project) : LokkalConfiguration, LokkalTableBuilder {

    internal val table: LocaleTable = LocaleTable.create()
    private val textBuilder = LokkalTextBuilderImpl(table)

    override fun getLogger(): Logger = project.logger

    override val resourceName: Property<String> = project.objects.property<String>()
        .convention("strings")

    override val defaultLocale: Property<Locale> = project.objects.property<Locale>()
        .convention(null)

    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()
        .convention(
            project.layout.projectDirectory
                .dir("src${File.separator}main${File.separator}resources")
        )

    override fun text(key: String, configuration: Action<LokkalTextBuilder>) {
        textBuilder.currentRow = key
        configuration(textBuilder)
    }
}