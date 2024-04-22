package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // 아래 파이널을 붙은 생성자를 자동으로 만들어준다.
public class BasicItemController {

    private final ItemRepository itemRepository;

//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }


    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();

        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price ,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute ("item") Item item, Model model){
        //자동으로 객채만들고 셋해줌

        itemRepository.save(item);
//        model.addAttribute("item", item);
//        위에 모델 에트도만들면 위 코드도 생략할 수 있음 (자동으로만듦)

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model){
        //자동으로 객채만들고 셋해줌

        itemRepository.save(item);
//        model.addAttribute("item", item);
//        위에 모델 에트도만들면 위 코드도 생략할 수 있음 (자동으로만듦)
//        키이름을 제거해도 클래스명을 소문자로 바꿔서 모델에튜에 넣어줌

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item){
        //자동으로 객채만들고 셋해줌

        itemRepository.save(item);
//        model.addAttribute("item", item);
//        위에 모델 에트도만들면 위 코드도 생략할 수 있음 (자동으로만듦)
//        키이름을 제거해도 클래스명을 소문자로 바꿔서 모델에튜에 넣어줌
//        모델까지 전부 제거 가능
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item){
        //자동으로 객채만들고 셋해줌

        itemRepository.save(item);
//        model.addAttribute("item", item);
//        위에 모델 에트도만들면 위 코드도 생략할 수 있음 (자동으로만듦)
//        키이름을 제거해도 클래스명을 소문자로 바꿔서 모델에튜에 넣어줌
//        모델까지 전부 제거 가능

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        //자동으로 객채만들고 셋해줌

        Item savedItem = itemRepository.save(item);
//        model.addAttribute("item", item);
//        위에 모델 에트도만들면 위 코드도 생략할 수 있음 (자동으로만듦)
//        키이름을 제거해도 클래스명을 소문자로 바꿔서 모델에튜에 넣어줌
//        모델까지 전부 제거 가능

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status" , true);
        //아래꺼는 쿼리 파라미터 형식으로 들어감

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId , Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";

    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId ,@ModelAttribute Item item){
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";

    }

    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000,10));
        itemRepository.save(new Item("itemB", 20000,20));
    }
}
