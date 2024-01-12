package com.vesko.deals_zone.utils

import com.vesko.deals_zone.model.Deal
import com.vesko.deals_zone.viewmodel.DealsViewModel

val mockDealsList = arrayListOf(
    Deal(
        id = "1",
        title = "Utopia Bedding Throw Pillows Insert (Pack of 2, White) - 18 x 18 Inches Bed and Couch Pillows - Indoor Decorative Pillows",
        price = "15.99",
        realPrice = "21.99",
        category = "home",
        link = "https://www.amazon.com/Utopia-Bedding-Throw-Pillows-Insert/dp/B01NBNDC1T/",
        description = "•50%Viscose, 30%Polyamide, 20%Polyester\n" +
                "•Imported\n" +
                "•Pull On closure\n" +
                "•Machine Wash\n" +
                "•Soft Material: This womens crew neck sweater is made of 50%Viscose+30%Polyamide+20% Polyester, soft and skin-friendly high quality knitted material, comfortable to touch and wear.\n" +
                "•Great Features: Crewneck long sleeve sweater, long tunic tops for leggings,side split hemline,vertical grain cuffs makes it more chic.\n" +
                "•Occasions: Whether you are daily wear, lounging, home, work, club, party, dating, vacation and Christmas holiday etc, this cute sweater tops will be your perfect companion. trendy and fashionable look.\n" +
                "•Must-have Collection: This slightly oversized sweater is so soft and casual! It will pair gorgeously with blue jeans or rich colored fall skinnies! And because of the neckline, you can show off your necklace.Unique outside seam design brings stylish looks.\n" +
                "•Garment Care: Gentle machine wash or hand wash in cold, do not bleach, flat dry.",
        imageDeal = "https://m.media-amazon.com/images/I/81dN52hESNL._SL1500_.jpg",
        hasCoupon = false
    ),
    Deal(
        id = "2",
        title = "Utopia Bedding Throw Pillows Insert (Pack of 2, White) - 18 x 18 Inches Bed and Couch Pillows - Indoor Decorative Pillows",
        price = "1.99",
        realPrice = "21.99",
        category = "home",
        link = "https://www.amazon.com/Utopia-Bedding-Throw-Pillows-Insert/dp/B01NBNDC1T/",
        description = "• 50%Viscose, 30%Polyamide, 20%Polyester\n" +
                "• Imported\n" +
                "• Pull On closure\n" +
                "• Machine Wash\n" +
                "• Soft Material: This womens crew neck sweater is made of 50%Viscose+30%Polyamide+20% Polyester, soft and skin-friendly high quality knitted material, comfortable to touch and wear.\n" +
                "• Great Features: Crewneck long sleeve sweater, long tunic tops for leggings,side split hemline,vertical grain cuffs makes it more chic.\n" +
                "• Occasions: Whether you are daily wear, lounging, home, work, club, party, dating, vacation and Christmas holiday etc, this cute sweater tops will be your perfect companion. trendy and fashionable look.\n" +
                "• Must-have Collection: This slightly oversized sweater is so soft and casual! It will pair gorgeously with blue jeans or rich colored fall skinnies! And because of the neckline, you can show off your necklace.Unique outside seam design brings stylish looks.\n" +
                "• Garment Care: Gentle machine wash or hand wash in cold, do not bleach, flat dry.",
        imageDeal = "https://m.media-amazon.com/images/I/81dN52hESNL._SL1500_.jpg"
    ),
    Deal(
        id = "3",
        title = "Utopia Bedding Throw Pillows Insert (Pack of 2, White) - 18 x 18 Inches Bed and Couch Pillows - Indoor Decorative Pillows",
        price = "5.99",
        realPrice = "1.99",
        category = "home",
        link = "https://www.amazon.com/Utopia-Bedding-Throw-Pillows-Insert/dp/B01NBNDC1T/",
        description = "• Fabric Type 59% Recycled Polyamide, 41% Polyamide\n" +
                "• Care Instructions Hand Wash Only\n" +
                "• Origin Imported\n" +
                "• Closure Type Clasp\n" +
                "• Country of Origin Vietnam",
        imageDeal = "https://m.media-amazon.com/images/I/81dN52hESNL._SL1500_.jpg"
    ),
)


val mockDealUiState = DealsViewModel.UiState(
    status = DealsViewModel.Status.DONE,
    list = mockDealsList,
    filteredList = arrayListOf(),
    searchBarText = "",
    favoriteSavedDeals = arrayListOf(),
    dealsByCategory = arrayListOf()
)