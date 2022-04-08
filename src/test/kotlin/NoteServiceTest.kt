import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun addNote_check_id() {
        val service = NoteService()
        val unexpectedResult = 0
        assertNotEquals(unexpectedResult, service.addNote(Note()).id)
    }

    @Test
    fun createComment_should_create() {
        val service = NoteService()
        val commentsSizeBefore = service.comments.size
        service.addNote(Note())
        service.createComment(Comment(noteId = 1))
        val commentsSizeAfter = service.comments.size
        assertTrue(commentsSizeAfter == commentsSizeBefore + 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun create_comment_should_throw() {
        val service = NoteService()
        service.addNote(Note())
        service.createComment(Comment(noteId = 55))
    }

    @Test
    fun deleteNote_Should_delete() {
        val service = NoteService()
        val note1 = service.addNote(Note())
        val note2 = service.addNote(Note())
        val notesSizeBefore = service.notes.size
        val deletedNotesSizeBefore = service.deletedNotes.size
        service.deleteNote(note2)
        val notesSizeAfter = service.notes.size
        val deletedNotesSizeAfter = service.deletedNotes.size
        assertTrue(notesSizeAfter == notesSizeBefore - 1 && deletedNotesSizeAfter == deletedNotesSizeBefore + 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteNote_Should_throw() {
        val service = NoteService()
        service.deleteNote(Note())
    }

    @Test
    fun deleteComment_Should_delete() {
        val service = NoteService()
        val note = service.addNote(Note())
        val comment = service.createComment(Comment(noteId = 1))
        val commentsSizeBefore = service.comments.size
        val deletedCommentsSizeBefore = service.deletedComments.size
        service.deleteComment(comment)
        val commentsSizeAfter = service.comments.size
        val deletedCommentsSizeAfter = service.deletedComments.size
        assertTrue(commentsSizeAfter == commentsSizeBefore - 1 && deletedCommentsSizeAfter == deletedCommentsSizeBefore + 1)
    }

    @Test(expected = CommentNotFoundException::class)
    fun deleteComment_Should_throw() {
        val service = NoteService()
        service.deleteComment(Comment())
    }

    @Test
    fun editNote_Existing() {
        val service = NoteService()
        val note = service.addNote(Note())
        val noteUpd = Note(id = 1, text = "upd")
        val result = service.editNote(noteUpd)
        assertTrue(result)
    }

    @Test
    fun editNote_UnExisting() {
        val service = NoteService()
        val note = service.addNote(Note())
        val noteUpd = Note(id = 25, text = "upd")
        val result = service.editNote(noteUpd)
        assertFalse(result)
    }

    @Test
    fun editComment_Existing() {
        val service = NoteService()
        val note = service.addNote(Note())
        val comment = service.createComment(Comment(noteId = 1))
        val commentUpd = Comment(id = 1, noteId = 1, text = "upd")
        val result = service.editComment(commentUpd)
        assertTrue(result)
    }

    @Test
    fun editComment_UnExisting() {
        val service = NoteService()
        val note = service.addNote(Note())
        val comment = service.createComment(Comment(noteId = 1))
        val commentUpd = Comment(id = 4, noteId = 1, text = "upd")
        val result = service.editComment(commentUpd)
        assertFalse(result)
    }

    @Test
    fun getNotes_should_get() {
        val service = NoteService()
        val note1 = service.addNote(Note(ownerId = 56))
        val note2 = service.addNote(Note(ownerId = 56))
        val expectedResult = mutableListOf(note1, note2)
        val result = service.getNotes(56)
        assertEquals(expectedResult, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getNotes_should_throw() {
        val service = NoteService()
        val note1 = service.addNote(Note(ownerId = 56))
        val note2 = service.addNote(Note(ownerId = 56))
        service.getNotes(22)
    }

    @Test
    fun getNoteById_Should_get() {
        val service = NoteService()
        val note1 = service.addNote(Note())
        val note2 = service.addNote(Note())
        val expectedResult = note2
        val result = service.getNoteById(2)
        assertEquals(expectedResult, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getNoteById_should_throw() {
        val service = NoteService()
        val note1 = service.addNote(Note())
        val note2 = service.addNote(Note())
        service.getNoteById(26)
    }

    @Test
    fun getCommentsByNote_Should_get() {
        val service = NoteService()
        val note1 = service.addNote(Note())
        val comment1 = service.createComment(Comment(noteId = 1))
        val comment2 = service.createComment(Comment(noteId = 1))
        val expectedResult = mutableListOf(comment1, comment2)
        val result = service.getCommentsByNote(note1)
        assertEquals(expectedResult, result)
    }

    @Test
    fun restoreComment_Should_Restore() {
        val service = NoteService()
        val note1 = service.addNote(Note())
        val comment1 = service.createComment(Comment(noteId = 1))
        val comment2 = service.createComment(Comment(noteId = 1))
        service.deleteComment(comment1)
        assertTrue(service.restoreComment(comment1))
//        val commentsSizeBefore = service.comments.size
//        val deletedCommentsSizeBefore = service.deletedComments.size
//        service.restoreComment(comment1)
//        val commentsSizeAfter = service.comments.size
//        val deletedCommentsSizeAfter = service.deletedComments.size
//        assertTrue(commentsSizeAfter == commentsSizeBefore + 1 && deletedCommentsSizeAfter == deletedCommentsSizeBefore - 1)
    }

    @Test(expected = CommentNotFoundException::class)
    fun restoreComment_Should_throw() {
        val service = NoteService()
        val note1 = service.addNote(Note())
        val comment1 = service.createComment(Comment(noteId = 1))
        //service.createComment(Comment(id = 8))
        service.restoreComment(comment1)
    }
}
