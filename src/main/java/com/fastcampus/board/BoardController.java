package com.fastcampus.board;

import com.fastcampus.board.Entity.BoardEntity;
import com.fastcampus.board.Repository.BoardRepository;
import com.fastcampus.board.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {
    // TODO BoardController는 JSP를 이용한 화면 로직과 연결되어 있습니다.
    //  다른 부분은 바꾸지 말고 TODO 부분만 작성해주시기를 권장합니다 :)
    // TODO please write code on only TODO part in BoardController
    @Autowired
    private BoardRepository repository;

    @RequestMapping(value="/save",method = RequestMethod.POST)
    public String createPost(@ModelAttribute("command") PostDto postDto){

        /* TODO: 게시물 추가 로직*/
        if(postDto.isEmpty()) // title, writer, content 가 빈값이면 에러 발생
            return "error";

        BoardEntity entry = new BoardEntity();

        entry.setTitle(postDto.getTitle());
        entry.setContent(postDto.getContent());
        entry.setWriter(postDto.getNickName());
        entry.setRegDate(LocalDateTime.now());

        repository.save(entry);

        return "redirect:/"; // 추가 후 홈 화면으로
    }

    @RequestMapping("/")
    public String ReadAllPost(Model model){

        /* TODO 게시물 전체를 받아오는 로직 */
        List<BoardEntity> list = repository.findAll();
        List<PostDto> postList = new ArrayList<>();

        for( BoardEntity entry : list)
        {
            entry.setCnt(entry.getCnt()+1);
            repository.save(entry);

            PostDto postDto = new PostDto();

            postDto.setPostId(entry.getSeq());
            postDto.setNickName(entry.getWriter());
            postDto.setTitle(entry.getTitle());
            postDto.setContent(entry.getContent());
            postDto.setCnt(entry.getCnt());
            postList.add( postDto);
        }

        model.addAttribute("postList", postList);
        return "index";
    }

    @RequestMapping(value="/update",method = RequestMethod.POST)
    public String updatePost(@ModelAttribute("command") PostDto postDto){

        /* TODO 게시물 수정 로직 */
        if(postDto.isEmpty()) // title, writer, content 가 빈값이면 에러 발생
            return "error";

        BoardEntity entry = repository.findById(postDto.getPostId());

        if( entry != null) {
            entry.setContent(postDto.getContent());
            entry.setTitle(postDto.getTitle());
            entry.setWriter(postDto.getNickName());
            entry.setCnt(entry.getCnt()+1);
            repository.save(entry);
        }
        return "redirect:/";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    public String deletePost(@PathVariable int id){

        /* TODO 게시물 삭제 로직 */
        BoardEntity entry = new BoardEntity();
        entry.setSeq((long) id);
        repository.delete(entry);

        return "redirect:/";
    }

    // ***************************************************************************************************
    // ******************************************* Do not edit *******************************************
    // 아래 부분은 수정 안하셔도 됩니다. (글 생성, 글 업데이트 창으로 연결하는 부분)

    @RequestMapping(value="/createView")
    public String ViewCreate(Model model){
        model.addAttribute("command", new PostDto());
        return "create";
    }

    @RequestMapping(value="/updateView/{id}")
    public String ViewUpdate(@PathVariable int id, Model model){
        PostDto postDto = new PostDto();
        postDto.setPostId(id);
        model.addAttribute("command",postDto);
        return "update";
    }
}

