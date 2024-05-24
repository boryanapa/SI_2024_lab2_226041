# Втора лабораториска вежба по Софтверско инженерство

## Борјана Поп-Атанасова, бр. на индекс 226041

### Control Flow Graph

Секоја бројка претставува една линија код. На следниот извадок од код, со коментар до секоја линија, која претставува теме, е прикажана соодветната бројка:

    public static boolean checkCart(List<Item> allItems, int payment){
        if (allItems == null){//1
            throw new RuntimeException("allItems list can't be null!");//2
        }

        float sum = 0;//3

        for (int i = 0; i < allItems.size(); i++){//4.1, 4.2, 4.3
            Item item = allItems.get(i);//5
            if (item.getName() == null || item.getName().length() == 0){//6
                item.setName("unknown");//7
            }
            if (item.getBarcode() != null){//8
                String allowed = "0123456789";//9
                char chars[] = item.getBarcode().toCharArray();//10
                for (int j = 0; j < item.getBarcode().length(); j++){//11.1,11.2,11.3
                    char c = item.getBarcode().charAt(j);//12
                    if (allowed.indexOf(c) == -1){//13
                        throw new RuntimeException("Invalid character in item barcode!");//14
                    }
                }
                if (item.getDiscount() > 0){//15
                    sum += item.getPrice()*item.getDiscount();//16
                }
                else {
                    sum += item.getPrice();//17
                }
            }
            else {
                throw new RuntimeException("No barcode!");//18
            }
            if (item.getPrice() > 300 && item.getDiscount() > 0 && item.getBarcode().charAt(0) == '0'){//19
                sum -= 30;//20
            }
        }
        if (sum <= payment){//21
            return true;//22
        }
        else {
            return false;//23
        }
    }//24

![CFG](https://github.com/boryanapa/SI_2024_lab2_226041/blob/master/SI_Lab2.png)

### Цикломатска комплексност

Цикломатската комплексност на дадениот код e 10. Овој резултат се добива со броење на предикатните јазли. Имаме 7 if услови и 2 for циклуси (со услов во нив за да излеземе од циклус). Истиот резултат можеме да го добиеме и со формулата за цикломатска комплексност C = E - N + 2 * P

### Тест случаи според критериумот Every branch

![Test cases picture](https://github.com/boryanapa/SI_2024_lab2_226041/blob/master/EveryBranch.png)

1. allItems = null, payment = 500
2. allItems = [(name="", barcode=null,  price=100, discount=0)], payment = 500
3. allItems = [(name="name1", barcode="A123456789", price=100, discount=0)], payment = 500
4. allItems = [(name="name2", barcode="0123456789",  price=200, discount=0)], payment = 100
5. allItems = [(name="name3", barcode="1234567890", price=500, discount=5)], payment = 2700

### Тест случаи според критериумот Multiple condition

![Test cases picture](https://github.com/boryanapa/SI_2024_lab2_226041/blob/master/MultipleCondition.png)

1. allItems=[(name= "name1", barcode = "05267893560", price = 430, discount =0.2), 
(name = "name2", barcode = "1234789560", price = 310, discount = 0.35), 
(name = "name3", barcode = "1234098765", price = 840, discount = 0), 
(name = "name4" barcode = "01238945678", price = 140, discount = 0.1)], payment = 90

### Објаснување на напишаните unit tests

Unit test-от „testCheckCartEveryBranch“, треба да го тестира checkCart методот од класата SILab2 по критериумот Every branch. 
Првиот дел проверува дали методот checkCart исфрла RuntimeException кога allItems е null.
Користам assertThrows за да го фатам исклучокот и проверувам дали пораката е "allItems list can't be null!". 
Вториот дел проверува дали методот исфрла RuntimeException кога item во листата нема баркод (null).
Исто така користам assertThrows за да го фатам исклучокот и проверувам дали пораката е „No barcode!“.
Третиот дел проверува дали методот исфрла RuntimeException кога item во списокот има баркод што содржи неважечки знаци (пример буквата 'A'). 
Како и во останатите два случаи, проверувам дали пораката е „Invalid character in item barcode!“.
Четвртиот дел тестира кога вкупната цена на производите би го надминувало payment, а користам assertFalse за да потврдам дека checkCart враќа false кога sum > payment (нема доволно средства).
И за крај, петтиот дел тестира кога вкупната цена на производите не би го надминувало payment, наспроти претходно спомнатото assertFalse, во овој случај користам assertTrue за да потврдам дека checkCart враќа true кога sum <= payment (вкупна цена на производите е 2470, а платено е 2700).

Unit test-от „testCheckCartMultipleCondition“, треба да го тестира If-условот во checkCart методот од класата SILab2 по критериумот Multiple condition.
Секој item е создаден со специфични атрибути за тестирање на трите услови во предикатот.
TTT-Сите услови (цена > 300, попуст > 0 и баркодот започнува со '0') се вистинити.
TTF-Првите два услови се вистинити (цена > 300 и попуст > 0), но третиот (баркодот започнува со '0') е невистинит.
TFX: Првиот услов е вистинит (цена > 300), вториот е неточен (попуст > 0), а третиот не е важно дали е вистинит или пак невистинит, поради тоа што трите услови се сврзани со „&&“ (означено со X).
FXX: Првиот услов е неточен (цена > 300), а како претходно што кажав,  другите услови не е важно дали се вистинити или не.
Откако ги направив items-те, потврдувам дека item1 ги исполнува сите услови (TTT), item2 ги исполнува првите два услови (TTF), item3 го исполнува само првиот услов (TFX), item4 не го исполнува првиот услов (FXX).

