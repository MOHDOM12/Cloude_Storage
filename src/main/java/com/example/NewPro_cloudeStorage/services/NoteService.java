package com.example.NewPro_cloudeStorage.services;

import com.example.NewPro_cloudeStorage.mapper.NotesMapper;
import com.example.NewPro_cloudeStorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }



    public Integer addNote(Note note, Integer userid){
        return notesMapper.insertNote(new Note(note.getNotetitle(),note.getNotedescription(),userid));
    }

    public Integer updateNote(Note note){
        return notesMapper.updateNote(note);
    }

    public Integer deleteNote(Integer noteid){
        return notesMapper.deleteNote(noteid);
    }

    public List<Note> getNotes(Integer userid){
        List<Note> notes = notesMapper.getNotes(userid);
        return notes;
    }

}
