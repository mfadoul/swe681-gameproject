package edu.gmu.swe.gameproj.controller;


import edu.gmu.swe.gameproj.ejb.cards.Card;
import edu.gmu.swe.gameproj.ejb.cards.CardFactory;
import edu.gmu.swe.gameproj.ejb.cards.action.Action;
import edu.gmu.swe.gameproj.ejb.cards.action.ActionDto;
import edu.gmu.swe.gameproj.jpa.Player;
import edu.gmu.swe.gameproj.models.ActionVm;
import edu.gmu.swe.gameproj.models.BuyVm;
import edu.gmu.swe.gameproj.validator.ActionVmValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping(value="/play/*")
public class PlayController {
    protected final Log logger = LogFactory.getLog(getClass());

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        if(binder.getTarget() instanceof ActionVm) {
            binder.setValidator(new ActionVmValidator());

        }
    }

    @RequestMapping(value="action", method= RequestMethod.POST)
    public ModelAndView action(@Valid ActionVm vm, BindingResult result, Model model) {
        ModelAndView mav = new ModelAndView();
        if(!result.hasErrors()){
            //TODO Other validations, like does player have the card

            Action playedCard = (Action)CardFactory.buildCard(vm.playedCardName);
            ActionDto dto = new ActionDto();
            dto.player = new Player();//TODO Replace with session player
            dto.oldCardName = vm.oldCardName;
            dto.newCardName = vm.newCardName;
            dto.discardCardNames = (ArrayList<String>)Arrays.asList(vm.discardCardNames);

//            if(vm.discardCardNames != null && vm.discardCardNames.length > 0) {
//                ArrayList<Card> discardCards = new ArrayList<Card>();
//                for (String s : vm.discardCardNames) {
//                    discardCards.add(CardFactory.buildCard(s));
//                }
//                dto.discardCardNames = discardCards;
//            }

            try {
                playedCard.Act(dto);
            }
            catch(InvalidParameterException ex){
                //TODO
            }
            catch(NullPointerException ex){
                //TODO
            }


        }
        else{
            //TODO Add errors
        }
        //TODO set mav values

        return mav;
    }

    @RequestMapping(value="buy", method= RequestMethod.POST)
    public ModelAndView buy(@Valid BuyVm vm, BindingResult result, Model model){
        ModelAndView mav = new ModelAndView();

        if(!result.hasErrors()) {
            Card playedCard = CardFactory.buildCard(vm.cardName);
        }
        else{
            //TODO Add errors
        }
        //TODO Set mav values
        return mav;
    }

}
