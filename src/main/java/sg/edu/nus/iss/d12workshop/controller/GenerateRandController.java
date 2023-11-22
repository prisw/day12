package sg.edu.nus.iss.d12workshop.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.d12workshop.exception.RandNoException;
import sg.edu.nus.iss.d12workshop.model.Generate;

@Controller
@RequestMapping (path = {"/random"})
public class GenerateRandController {

    @GetMapping(path ="/show")
    //instantiate object and map to the webpage
    public String showRandomForm(Model m){
        Generate g = new Generate();
        m.addAttribute("generateObj", g);
        return "generate";
    }

    @GetMapping(path="/generate")
    //then the button generate is clicked, it will return here to randomize number
    public String generate(@RequestParam Integer numberVal, Model m){
       this.randomizeNumber(m, numberVal.intValue());
        return "result";
    }

    private void randomizeNumber(Model m, int noOfGenerateNo){
        int MaxGenNo =30;
        String[] imgNumbers = new String[MaxGenNo];

        //validate no of generator number cannot be less than 1 or more than 30
        if (noOfGenerateNo < 1 || noOfGenerateNo > MaxGenNo){
            throw new RandNoException();
        }


        //generator number images filename and set to the array
        for(int i=0 ;i<= MaxGenNo; i++){
            System.out.println();
            imgNumbers[i] = "number" + i + ".jpg";
        }

        //map generated numbers to filename, store it as a List.
        List<String> selectedImgs = new ArrayList<String>();
        Random rand = new Random();
        //make the numbers as set , so it won't duplicate.
        Set<Integer> uniqueResult = new LinkedHashSet<Integer>();
        while(uniqueResult.size() < noOfGenerateNo) {
            Integer randNoResult = rand.nextInt(MaxGenNo);
            if(randNoResult !=null){
                if(randNoResult > 0)
                uniqueResult.add(randNoResult);
            }
        }
        //mapping, iterating
        Integer currElem = null;
        for(Iterator iter = uniqueResult.iterator(); iter.hasNext();){
        currElem = (Integer)iter.next();
        if(currElem != null){
        selectedImgs.add(imgNumbers[currElem]);
    }

    //send desired number that you want to generate to the page back
    m.addAttribute("noOfGenerateNo", noOfGenerateNo);
    m.addAttribute("selectedImgs", selectedImgs);
    System.out.println(">>>" + selectedImgs);
    
    }}


    
}
