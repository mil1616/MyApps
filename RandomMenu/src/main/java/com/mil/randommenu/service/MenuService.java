/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.service;

import com.mil.randommenu.dao.MenuDao;
import com.mil.randommenu.dao.MenuItemDao;
import com.mil.randommenu.dao.VegetableDao;
import com.mil.randommenu.dao.WeekItemDao;
import com.mil.randommenu.model.Menu;
import com.mil.randommenu.model.MenuItem;
import com.mil.randommenu.model.Vegetable;
import com.mil.randommenu.model.WeekItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
//import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;
//import javax.activation.*;

/**
 *
 * @author Gael Sonney
 */
@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS)
public class MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private VegetableDao vegetableDao;
    @Autowired
    private WeekItemDao weekItemDao;
    @Autowired
    private MenuItemDao menuItemDao;

    public MenuService() {
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addMenu(Menu menu) {
        menu.setIsWeekMenu(false);
        menuDao.saveMenu(menu);
    }

    public Menu getMenu(long menuId) {
        return menuDao.getById(menuId);
    }

    public Menu getMenuFromMenuItemId(String menuItemId) {
        return menuDao.getById(menuItemDao.getById(Long.valueOf(menuItemId)).getMenu().getId());
    }

    public List<Menu> getAllMenus() {
        return menuDao.getAll();
    }

    @Transactional(readOnly = false)
    public void deleteMenu(long menuId) {
        List<MenuItem> menuItems = menuItemDao.getMenuItemFromMenu(menuDao.getById(menuId));
        for (MenuItem menuitem : menuItems) {
            menuItemDao.delete(menuitem.getId());
        }
        menuDao.delete(menuId);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addVegetable(Vegetable vegetable) {
        vegetableDao.saveVegetable(vegetable);
    }

    public List<Vegetable> getAllVegetables() {
        return vegetableDao.getAll();
    }

    public List<MenuItem> getMenuItemsFromMenu(Long menuId) {
        if (menuId != null) {
            return menuItemDao.getMenuItemFromMenu(getMenu(menuId));
        }
        return new ArrayList<>();
    }

    public List<Vegetable> getVegetablesFromMenu(Long menuId) {
        if (menuId != null) {
            return menuItemDao.getVegetablesFromMenu(getMenu(menuId));
        }
        return new ArrayList<>();
    }

    public List<Vegetable> getAllVegetablesNotInMenu(Long menuId) {

        List<Vegetable> vedgeList = vegetableDao.getAll();

        if (menuId != null) {
            List<Vegetable> list = getVegetablesFromMenu(menuId);
            vedgeList.removeAll(list);
        }
        Collections.sort(vedgeList);
        return vedgeList;
    }

    @Transactional(readOnly = false)
    public void deleteVegetable(long vegetableId) {
        vegetableDao.delete(vegetableId);
    }

    @Transactional(readOnly = false)
    public void addToMenu(long menuId, long vegetableId) {
        MenuItem menuItem = MenuItem.getInstanceMenuItem(menuDao.getById(menuId), vegetableDao.getById(vegetableId));
        menuItemDao.saveMenuItem(menuItem);
        addOrUpdateWeekItem(menuItem);
    }

    @Transactional(readOnly = false)
    public void removeFromMenu(long menuId, long menuItemId) {
        MenuItem menuItem = menuItemDao.getById(menuItemId);
        menuItem.setQuantity(0);
        menuItemDao.saveMenuItem(menuItem);
        removeOrUpdateWeekItem(menuItemId);
        menuItemDao.delete(menuItemId);
    }

    public List<Menu> getWeekMenus() {
        return menuDao.getByIsWeekMenu(true);
    }

    public List<Menu> getNotWeekMenus() {
        return menuDao.getByIsWeekMenu(false);
    }

    @Transactional(readOnly = false)
    public void addMenuToWeek(long menuId) {
        Menu menu = getMenu(menuId);
        menu.setIsWeekMenu(true);
        menuDao.saveMenu(menu);

        for (MenuItem menuItem : getMenuItemsFromMenu(menuId)) {
            addOrUpdateWeekItem(menuItem);
        }
    }

    public void addOrUpdateWeekItem(MenuItem menuItem) {
        WeekItem weekItem = weekItemDao.getWeekItemFromVegetable(menuItem.getVegetable());
        if (weekItem != null) {
            Long count = menuItemDao.countVegetableFromWeekMenus(menuItem.getVegetable());
            if (count != null) {
                weekItem.setQuantity(count.intValue());
            }
        } else {
            weekItem = WeekItem.getInstance(menuItem.getVegetable(), menuItem.getQuantity());
        }
        weekItemDao.saveWeekItem(weekItem);
    }

    @Transactional(readOnly = false)
    public void removeMenuFromWeek(long menuId) {
        Menu menu = getMenu(menuId);
        menu.setIsWeekMenu(false);
        menuDao.saveMenu(menu);

        for (MenuItem menuItem : getMenuItemsFromMenu(menuId)) {
            removeOrUpdateWeekItem(menuItem);
        }
    }

    public void removeOrUpdateWeekItem(MenuItem menuItem) {
        WeekItem weekItem = weekItemDao.getWeekItemFromVegetable(menuItem.getVegetable());
        if (weekItem != null) {
            Long count = menuItemDao.countVegetableFromWeekMenus(menuItem.getVegetable());
            if (count != null) {
                weekItem.setQuantity(count.intValue());
                weekItemDao.saveWeekItem(weekItem);
            } else {
//                weekItemDao.delete(weekItem.getId());
            }
        }
    }

    public void removeOrUpdateWeekItem(long menuItemId) {
        MenuItem menuItem = menuItemDao.getById(menuItemId);
        removeOrUpdateWeekItem(menuItem);
    }

    @Transactional(readOnly = false)
    public void saveQuantity(Integer menuQuantity, String menuItemId) {
        MenuItem menuItem = menuItemDao.getById(Long.valueOf(menuItemId));
        Integer oldQuantity = menuItem.getQuantity();
        Integer diff;
        if (oldQuantity != null) {
            diff = menuQuantity - oldQuantity;
        } else {
            diff = 1;
        }
        menuItem.setQuantity(menuQuantity);
        menuItemDao.saveMenuItem(menuItem);
        if (diff > 0) {
            addOrUpdateWeekItem(menuItem);
        } else if (diff < 0) {
            removeOrUpdateWeekItem(menuItem);
        }
    }

    @Transactional(readOnly = false)
    public void saveQuantityInFridge(Integer quantity, String weekItemId) {
        WeekItem weekItem = weekItemDao.getById(Long.valueOf(weekItemId));
        weekItem.setFridgeQuantity(quantity);
        weekItemDao.saveWeekItem(weekItem);
    }

    public Integer getQuantityFromVegetable(Vegetable vegetable) {
        Long i = menuItemDao.countVegetableFromWeekMenus(vegetable);
        return (i != null ? i.intValue() : 0);
    }

    public List<WeekItem> getWeekItems() {
        List<WeekItem> weekItems = weekItemDao.getWeekItems();
        Collections.sort(weekItems);
        return weekItems;
    }

    public void saveMenu(Menu menu) {
        menuDao.saveMenu(menu);
    }

    public void sendMail() {
         // Recipient's email ID needs to be mentioned.
//      String to = "mil1616@gmail.com";
//
//      // Sender's email ID needs to be mentioned
//      String from = "randommenu@orange.com";
//
//      // Assuming you are sending email from localhost
//      String host = "localhost";
////      String host = "smtp.gmail.com";
////      String host = "smtp.orange.net";
//      String port = "587";
//
//      // Get system properties
//      Properties properties = System.getProperties();
//
//      // Setup mail server
//      properties.setProperty("mail.smtp.host", host);
//      properties.setProperty("mail.smtp.port", "25");
////      properties.setProperty("mail.smtp.user", to);
//
//      // Get the default Session object.
//      Session session = Session.getDefaultInstance(properties);
//
//      try{
//         // Create a default MimeMessage object.
//         MimeMessage message = new MimeMessage(session);
//
//         // Set From: header field of the header.
//         message.setFrom(new InternetAddress(to));
//
//         // Set To: header field of the header.
//         message.addRecipient(Message.RecipientType.TO,
//                                  new InternetAddress(to));
//
//         // Set Subject: header field
//         message.setSubject("This is the Subject Line!");
//
//         // Now set the actual message
//         message.setText("This is actual message");
//
//         // Send message
//         Transport.send(message);
//         System.out.println("Sent message successfully....");

        final String username = "mil1616@gmail.com";
        final String password = "--telemark";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("to-email@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
