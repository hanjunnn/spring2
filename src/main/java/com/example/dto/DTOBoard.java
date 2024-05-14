package com.example.dto;

import com.example.entity.EntityBoard;

public class DTOBoard {
    public String Board_Title;
    public String Board_Content;
    public String Board_Author;
    public String Board_Date;
    public DTOBoard(){
    
    }
    public DTOBoard(String board_Title, String board_Content, String board_Author, String board_Date) {
        super();
        Board_Title = board_Title;
        Board_Content = board_Content;
        Board_Author = board_Author;
        Board_Date = board_Date;
    }
    public String getBoard_Title() {
        return Board_Title;
    }
    public void setBoard_Title(String board_Title) {
        Board_Title = board_Title;
    }
    public String getBoard_Content() {
        return Board_Content;
    }
    public void setBoard_Content(String board_Content) {
        Board_Content = board_Content;
    }
    public String getBoard_Author() {
        return Board_Author;
    }
    public void setBoard_Author(String board_Author) {
        Board_Author = board_Author;
    }
    public String getBoard_Date() {
        return Board_Date;
    }
    public void setBoard_Date(String board_Date) {
        Board_Date = board_Date;
    }
    public EntityBoard ToEntity() {
        return new EntityBoard(-1, this.Board_Title, this.Board_Content, this.Board_Author, this.Board_Date);
    }
    @Override
    public String toString() {
        System.out.println("DTOBoard [Board_Title=" + Board_Title + ", Board_Content=" + Board_Content + ", Board_Author=" + Board_Author + ", Board_Date=" + Board_Date + "]");
        return "DTOBoard [Board_Title=" + Board_Title + ", Board_Content=" + Board_Content + ", Board_Author=" + Board_Author + ", Board_Date=" + Board_Date + "]";
    }

}
