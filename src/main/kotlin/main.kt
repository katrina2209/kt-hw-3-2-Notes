fun main() {
    val service = NoteService()
//    val a = service.addNote(Note())
//    println(a)
//    val b = service.addNote(Note())
//    println(b)
//    val c = service.addNote(Note())
//    println(c)
//    val d = service.createComment(Comment(noteId = 3))
//    println(d)
//    val e = service.createComment(Comment(noteId = 2))
//    println(e)
//    val f = service.createComment(Comment(noteId = 2))
//    println(f)
    service.addNote(Note())
    service.createComment(Comment(noteId = 1))
    val e = service.createComment(Comment(noteId = 1))
    service.createComment(Comment(noteId = 1))
    println(service.comments)
    println(service.deletedComments)
//    service.deleteNote(b)
//    println(service.notes)
//    println(service.deletedNotes)
    service.deleteComment(e)
    println(service.comments)
    println(service.deletedComments)

    service.restoreComment(Comment(id=8))
    println(service.comments)
    println(service.deletedComments)
//
//    service.editNote(Note(id = 1, text = "dg"))
//    println(service.notes)
//
//    service.editComment(Comment(id = 2, text = "dfgv"))
//    println(service.comments)

//    service.addNote(Note(ownerId = 8))
//    service.addNote(Note(ownerId = 8))
//    service.addNote(Note(ownerId = 8))
//    println(service.getNotes(8))
//    println(service.getNoteById(5))
//
//    println(service.comments)
//
//    println(service.getCommentsByNote(b))
}