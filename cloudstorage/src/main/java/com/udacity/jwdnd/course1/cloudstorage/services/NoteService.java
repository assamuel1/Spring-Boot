package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
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

    public void createNote(Notes newNote, String username) {
       // Notes newNote = new Notes(null, noteform.getNoteTitle(),noteform.getNoteDescription(), userMapper.getUserIdByUsername(username));
        newNote.setUserId(userMapper.getUserIdByUsername(username));
        this.noteMapper.insertNote(newNote);
    }

    public List<Notes> getNotes(Integer userid)
        {
            return noteMapper.getAllNotes(userid);
        }

    public void deleteNote(int noteId) {
        noteMapper.deleteNotes(noteId);
    }

    public int getNoteBynoteId(int noteId) {
        return noteMapper.getNoteBynoteId(noteId);
    }

    public void updateNote(Notes editNote, String username) {
        //Notes editNote = new Notes(Integer.parseInt(updateNote.getnoteId()), updateNote.getNoteTitle(), updateNote.getNoteDescription(), userMapper.getUserIdByUsername(username));
        noteMapper.updateNotes(editNote);
    }





}
