// ISecurityCenter.aidl
package me.ewriter.art_chapter2.binderpool;

// Declare any non-default types here with import statements

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}
