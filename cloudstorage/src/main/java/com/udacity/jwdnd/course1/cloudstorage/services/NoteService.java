package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.forms.NoteModalForm;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {

        private NoteMapper noteMapper;
        private UsersMapper userMapper;


    public NoteService(NoteMapper noteMapper, UsersMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    @PostConstruct
        public void postConstruct() {
            System.out.println("Creating NoteService bean");
        }


    public void createNote(NoteModalForm noteform, String username) {

        Notes newNote = new Notes(null, noteform.getNoteTitle(),noteform.getNoteDescription(), userMapper.getUserIdByUsername(username));

        this.noteMapper.insertNote(newNote);
    }

    public List<Notes> getNotes(Integer userid)
        {
            return noteMapper.getAllNotes(userid);
        }

    public void deleteNote(int noteid) {
        noteMapper.deleteNotes(noteid);
    }

    public int getNoteByNoteId(int noteid) {
        return noteMapper.getNoteByNoteid(noteid);
    }

    public void updateNote(NoteModalForm noteModalForm, String username) {
        Notes editNote = new Notes(Integer.parseInt(noteModalForm.getNoteId()), noteModalForm.getNoteTitle(), noteModalForm.getNoteDescription(), userMapper.getUserIdByUsername(username));
        noteMapper.updateNotes(editNote);
    }





}
