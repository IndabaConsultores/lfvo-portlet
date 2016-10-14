package net.indaba.lostandfound.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;

/**
 * Portlet implementation class AppManagerPortlet
 */
public class ItemListPortlet extends MVCPortlet{
		
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {		
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<Item> items;
		
		try {		
			
			// 1) Pintamos la lista de categorias existente
			HashMap<String, String> catById = new HashMap<String, String>();
			List<AssetVocabulary> vocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(themeDisplay.getScopeGroupId(), false);			
			for(AssetVocabulary vocabulary: vocabularies){
				
				List<AssetCategory> categoryList = null;				
				categoryList = AssetCategoryLocalServiceUtil.getVocabularyCategories(vocabulary.getVocabularyId(), -1, -1, null); //List of all categories

				for(AssetCategory asset: categoryList){					
					String categoryName = asset.getName(); //get category Name
					long categoryId = asset.getCategoryId(); //get category id					
					
					catById.put(String.valueOf(categoryId), categoryName);
				}
			}
			renderRequest.setAttribute("catById", catById);
						
			// 2) Buscamos los items correspondientes
			String catIds = ParamUtil.getString(renderRequest, "catIds", ""); // Obtenemos las cats seleccionadas en el buscador previamente
			renderRequest.setAttribute("selectedCatIds", catIds);			
			
			// Cogemos todos los items
			items = ItemLocalServiceUtil.getOfficeItems(themeDisplay.getScopeGroupId(), -1, -1);			
			
			// Si la seleccion de categorias no es vacio...
			if(!"".equals(catIds)){
				
				List<Item> itemsFiltro = new ArrayList<Item>();
				for(Item item : items){
					
					AssetEntry ast = AssetEntryLocalServiceUtil.fetchEntry("net.indaba.lostandfound.model.Item", item.getPrimaryKey());
					List<AssetCategory> listaCatsDos = ast.getCategories();
					for(AssetCategory cat : listaCatsDos){					
						
						String catFilter = String.valueOf(cat.getCategoryId());
						if(catIds.toLowerCase().contains(catFilter.toLowerCase())){
							itemsFiltro.add(item);
							break;
						}						
					}					
				}
				renderRequest.setAttribute("items", itemsFiltro);
				
			// si es vacio, metemos todos los items.
			} else {				
				renderRequest.setAttribute("items", items);
				
			}		
				
		} catch (PortalException e) {
			e.printStackTrace();
		}
		
		super.doView(renderRequest, renderResponse);
	}
		
	public void filtrar(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException, PortalException {
		
		String[] categories = ParamUtil.getStringValues(actionRequest, "categories", null);
		
		String catIds = "";
		if(categories!= null){
			for(String value : categories){
				catIds = catIds + value + "/";
			}
			catIds = catIds.substring(0, catIds.length()-1); // Quitamos el '/' del final
		}
		
		actionResponse.setRenderParameter("catIds", catIds);		
	}
	
	public static final String PATH_ITEM_DETAIL = "/html/itemlist/item_detail.jsp";
}