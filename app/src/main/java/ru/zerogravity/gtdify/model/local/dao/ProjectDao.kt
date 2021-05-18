package ru.zerogravity.gtdify.model.local.dao

import io.objectbox.query.Query
import ru.zerogravity.gtdify.model.Project
import ru.zerogravity.gtdify.model.Section
import ru.zerogravity.gtdify.model.local.ObjectBox


object ProjectDao {
    private val projectBox = ObjectBox.store.boxFor(Project::class.java)

    fun updateName(projectId: Long, name: String) {
        val project = projectBox[projectId]
        project.name = name
        projectBox.put(project)
    }

    fun addSection(projectId: Long, section: Section) {
        val project = projectBox[projectId]
        val newSection = SectionDao.createSection(section)
        project.sections.add(newSection)
        projectBox.put(project)
    }

    fun getProjects(): Query<Project> {
        return projectBox.query().build()
    }

    fun createProject(project: Project): Long {
        return projectBox.put(project)
    }
}