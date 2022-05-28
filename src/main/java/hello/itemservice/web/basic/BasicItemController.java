package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping //HTML에 넣어주는 것
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add") //단순하게 보여주는 용도
    public String addForm(){
        return "basic/addForm";
    }
/**
    @PostMapping("/add")
    public String save(@RequestParam String itemName , @RequestParam int price, @RequestParam Integer quantity, Model model){

        Item item =new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item",item);

        return "basic/item";
    }

@PostMapping("/add")
public String save(@ModelAttribute ("item") Item item,Model model){ //   model.addAttribute("item",item); 아이템에 넣어주기것도 처리해준다
                                                                    //("item") 삭제해도 괜찮다 알아서 만들어 주기 때문에 , 이 때는 model 도 제거

    itemRepository.save(item);

    return "basic/item";
}


   @PostMapping("/add") //저장을 처리해주는 용도
   public String save(Item item){ //   model.addAttribute("item",item); 아이템에 넣어주는는도 처리해준다
                                             //("item") 삭제해도 괜찮다 알아서 만들어 주기 때문에
    itemRepository.save(item);
    return "basic/item";
    }
 **/

   @PostMapping("/add") //저장을 처리해주는 용도
     public String save(Item item, RedirectAttributes redirectAttributes){ //   model.addAttribute("item",item); 아이템에 넣어주는는도 처리해준다
    //("item") 삭제해도 괜찮다 알아서 만들어 주기 때문에
       Item savedItem = itemRepository.save(item);
       redirectAttributes.addAttribute("itemId",savedItem.getId());
       redirectAttributes.addAttribute("status",true);
       return "redirect:/basic/items/" + item.getId(); //새로고침을 했을 때 다른 id로 저장되는 것을 막기 위해서는 아예 새로운 호출인 redirect 필수
     }

   @GetMapping("/{itemId}/edit") //단순하게 보여주는 용도
    public String editForm(@PathVariable Long itemId,Model model){
       Item item = itemRepository.findById(itemId);
       model.addAttribute("item",item);
       return "basic/editForm";
   }

    @PostMapping("/{itemId}/edit") //상품 수정 처리 해주는 것
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item){
       itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }



}
