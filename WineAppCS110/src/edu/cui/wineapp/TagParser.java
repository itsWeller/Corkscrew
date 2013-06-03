package edu.cui.wineapp;

import android.util.Log;

public class TagParser {
	

	
	public void parse(DetailedWine dWine){
		
		String tags = "wine fruit red flavors finish aromas palate notes fresh wines sweet pinot rich well white nose long our grapes very vintage oak chardonnay blend acidity noir color great full tannins years ripe vineyard moscato bouquet yet sauvignon soft balanced und bodied fermentation perfect complex light citrus made black cherry valley bright mouth cabernet complexity spicy crisp spice peach rose good you blanc elegant like dry floral balance fruity now green tasting best dark vineyards texture months fruits yellow fine dishes delicate alcohol aromatic hint medium food apple taste fermented character clean style grape pear winery refreshing points through year hints fish drink colour vanilla mineral classic structure vibrant cool smooth aging then pink notes merlot aperitif intense mit lemon bottle pale melon strawberry new produced lively excellent sparkling subtle barrels river warm make zinfandel grown gold honey summer deep ideal estate powerful vines followed barrel hand orange grilled meat berries what creamy supple lovely these tropical cherries cheeses berry growing ros� napa dried temperature sonoma russian two aged time first franc concentrated glass quality still feel age flowers brut cassis cold small aroma french harvest where flavor shows production freshness gives delicious days body touch raspberry plum straw makes stainless flavours blossom steel round blackberry purple top old dessert beautiful low serve der high beautifully while bottled region spices early layers ruby sugar before exotic bottling herbs few release enjoy chilled including offers pineapple three appellation pressed following brilliant von chocolate enjoyed roasted peaches natural syrah both soil persistent blueberry note foods cellar variety lots asti juice finest yeast lingering showing firm tobacco planted champagne meats traditional international must richness perfectly tank slightly raspberries find velvety gris bubbles lime pepper san serving apricot aromen pressing gaumen harvested unique length winemaking wonderful cases licorice focused smoke violet expression bordeaux wild may honeysuckle blue abgang family minerality percentage winemaker qualities smoky even carneros france whole characters california just selected muscat county toasty quite cru nice drinking adds each special down your climate between once depth world grapefruit sweetness rather spring blancs easy place strong show mid provide order die asian stone another pure times accompaniment grenache addition wood horse day weather air pretty give malolactic selection delicately characteristics italy clear nuances beaune back blended strawberries picked american mocha level soils intensity recommended ginger rated hot lamb apricots produce highly refined here during per result many typical sun advocate forward fermenters class reveals seafood finishes call pair cinnamon competition potential producing acid should late offering served varieties grand august under young chicken salad resulting five lush savory mango pleasant santa harmonious parker especially caramel based prior integrated lightly rare sauce although superb structured further being earthy exceptional right tannin next varietal match displays semi found range cream exhibits nach semillon noten shellfish add clusters pairings highlights way itself sunshine petals established weeks almost business scents comes components currant use open known rex terroir soave racked controlled game com own process wein free certainly grass nase tea coast slight les frucht meyer allowed evident mellow blending super represents eye become component entry season art item spectator chablis total four silky steak six press direct burghound beach aftertaste elements matched elegance method perfumed fragrant extract limestone organic apples used historic different remarkable area interesting adding period spent sich persistence finishing gooseberry lasting those gently earth candied destemmed voluptuous leather desserts carefully golden vats sur making contact owned extraordinary rock precision north september aromatics mature several along date enough mediterranean everyday large create combine coffee fleshy maceration type tempranillo gentle already heart finesse cuisine again always pairs though tiers label hills robert dense seductive crushed crus built vine layered lingers aspects liqueur acres deliciously riesling toasted mushroom rioja friendly colored jam off pairing bottles soak reminiscent enjoyment flavour youth sea des tastes allen meadows winemakers since cava regions enticing put mostly allow arrival cab minerals bitter underscored perdrix power complemented issue hectares senses silver skins broad salmon morning using poultry roses jun less value southern amazing italian does mild frame beef minimal baked pts primary premier sensual enjoyable product peel above average famous ages ample combines vosne trousseau frizzante heavy ridge blood keep climates information distinctive skin fragrance real louis run certification block almonds wonderfully sleek rounded beaujolais concentrate hue garnet bit native mint until amount temperatures it�s domaine opens true ist heat passionfruit complement contributed results added bursting hill example aber blossoms opened often honeyed wide reviewed lees lobster outstanding wine�s vinification mouthfeel anselmi ein cedar secondary toast anderson better usually buttery month filtered dosage tangerine cuvee came maturity einem among nutmeg producers duarte technical cork iron zest smoked grow juicy particularly plenty impression burgundy combination reifen cut experience home south box called den reds canelli vegetables harvesting pedigree champs blackberries extraordinarily graphite k�rper ripening help within tanks villages eine roundness meant das anise filled chalky meditazione savoury throughout northern today flavorful regular really scent produces daniel appearance immediate ever �the residual solera system meal mandrarossa revealing truly features regularly stock across relatively price spain facing rains cuv�e difference www towards petit amber weight inside textured canopy caramelized mouthwatering patolitas las settle overnight change stopped linger amongst effort fairly accolades became retain chateau cinsault incense half too malbec rate district enhance developing attractive iwc lends crystal lavender seem saw expressive crush clone plush tiny riper focus packed shy offer completely gras foie profile cellaring favorite exclusively hearty understated considering vibrancy pastry almond hallmark crop tapanappa management fog part ripeness nuanced defined barbara remo acacia try twice sappy autumn evolve sip expansive strength nuance volume possess sparklings infused coat lie see provides floor partners gracefully remains ml bought conditions article name contra separately josh cooking room ready hold extremely costa kiwi second disgorging develop oaky mendocino central heady underbrush local thanks rhone pie viticultural background sterling leading fanucchi terrific leaves parcels turley table sehr framed having lighter champenoise softness slow exactly fig impressively forest cheese approximately looking risotto impressive cooled delightful creates dundee oregon allegro weich blosser sokol multi popular exceptionally spirits point accents non degrees";
		String[] tagArr = tags.split(" ");
		Log.e("TAG ARRAY SIZE",""+tagArr.length);
		for(String s : tagArr){
			if(dWine.getWm_notes().contains(s))
				dWine.setTags(dWine.getTags()+s+" ");
		}
	}

}
