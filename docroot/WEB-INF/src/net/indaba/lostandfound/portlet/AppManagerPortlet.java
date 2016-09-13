package net.indaba.lostandfound.portlet;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.util.portlet.PortletProps;

/**
 * Portlet implementation class AppManagerPortlet
 */
public class AppManagerPortlet extends MVCPortlet {
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		System.out.println(getPortletContext().getRealPath("/"));
		
		
		FirebaseOptions options = new FirebaseOptions.Builder()
			    .setDatabaseUrl("https://vivid-fire-8126.firebaseio.com/")
			    .setServiceAccount(AppManagerPortlet.class.getClassLoader().getResourceAsStream("firebase-service-account.json"))
			    .build();
			FirebaseApp.initializeApp(options);
			System.out.println(FirebaseApp.getApps().size());
			
			DatabaseReference ref = FirebaseDatabase
				    .getInstance()
				    .getReference("/offices");
			
			ref.addValueEventListener(new ValueEventListener() {
				
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					// TODO Auto-generated method stub
					System.out.println(dataSnapshot.getValue());
				}
				
				@Override
				public void onCancelled(DatabaseError arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
		super.doView(renderRequest, renderResponse);
	}
 

}
