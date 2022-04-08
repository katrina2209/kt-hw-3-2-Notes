class NoteService {
    val notes = mutableListOf<Note>()
    var comments = mutableListOf<Comment>()
    val deletedNotes = mutableListOf<Note>()
    val deletedComments = mutableListOf<Comment>()
    var currentNoteId = 1
    var currentCommentId = 1

    fun addNote(note: Note): Note {
        notes.add(note.copy(id = currentNoteId))
        currentNoteId++
        return notes.last()
    }

    fun createComment(comment: Comment): Comment {
        if (!findNoteById(comment.noteId)) {
            throw NoteNotFoundException()
        } else comments.add(comment.copy(id = currentCommentId))
        currentCommentId++
        return comments.last()
    }

    fun deleteNote(note: Note) {
        if (!findNoteById(note.id)) {
            throw NoteNotFoundException()
        } else {
            deletedNotes.add(note)
            notes.remove(note)
            deletedComments.addAll(getCommentsByNote(note))
            comments.removeAll(getCommentsByNote(note))
        }
    }

    fun deleteComment(comment: Comment) {
        if (!findCommentById(comment.id)) {
            throw CommentNotFoundException()
        } else {
            deletedComments.add(comment)
            comments.remove(comment)
        }
    }

    fun editNote(noteUpd: Note): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (noteUpd.id == note.id) {
                notes[index] = noteUpd.copy(id = note.id)
                return true
            }
        }
        return false
    }

    fun editComment(commentUpd: Comment): Boolean {
        for ((index, comment) in comments.withIndex()) {
            if (commentUpd.id == comment.id) {
                comments[index] = commentUpd.copy(id = comment.id)
                return true
            }
        }
        return false
    }

    fun getNotes(ownerId: Int): List<Note> {
        val notesOfUser = emptyList<Note>().toMutableList()
        for (note in notes) {
            if (ownerId == note.ownerId) notesOfUser += note
        }
        return notesOfUser.ifEmpty { throw NoteNotFoundException() }
    }

    fun getNoteById(noteId: Int): Note {
        for (note in notes) {
            if (note.id == noteId) {
                return note
            }
        }
        throw NoteNotFoundException()
    }

    fun getCommentsByNote(note: Note): List<Comment> {
        val commentsByNote = emptyList<Comment>().toMutableList()
        for (comment in comments) {
            if (note.id == comment.noteId) commentsByNote += comment
        }
        return commentsByNote
    }

    fun restoreComment(restoringComment: Comment): Boolean {
        val iterator = deletedComments.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.id == restoringComment.id) {
                iterator.remove()
                comments.add(item)
                comments = comments.sortedWith(compareBy { it.id }).toMutableList()
                return true
            }
        }
        throw CommentNotFoundException ()
        }


    fun findNoteById(noteId: Int): Boolean {
        for (note in notes) {
            if (note.id == noteId) {
                return true
            }
        }
        return false
    }

    fun findCommentById(commentId: Int): Boolean {
        for (comment in comments) {
            if (comment.id == commentId) {
                return true
            }
        }
        return false
    }

}
