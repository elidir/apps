//
//  Model.swift
//  CurrencyCources
//
//  Created by Alexander on 27/03/2019.
//  Copyright © 2019 Alexander. All rights reserved.
//

import UIKit

class Currency {
    var numCode: String?
    var charCode: String?
    var name: String?
    
    var nominal: String?
    var nominalDouble: Double?

    var value: String?
    var valueDouble: Double?
    
    var imageFlag: UIImage? {
        if let charCode = charCode {
            return UIImage(named: charCode)
        }
        return nil
    }
    
    class func rouble() -> Currency {
        let r = Currency()
        r.numCode = "810"
        r.charCode = "RUR"
        r.name = "Российский рубль"
        r.nominal = "1"
        r.nominalDouble = 1
        r.value = "1"
        r.valueDouble = 1
        
        return r
    }
}

class Model: NSObject, XMLParserDelegate {
    static let shared = Model()
    
    var currencies: [Currency] = []
    var currentDate: String = ""
    
    var fromCurrency: Currency = Currency.rouble()
    var toCurrency: Currency = Currency.rouble()
    
    func convert(amount: Double?) -> String {
        
        if amount == nil {
            return ""
        }
        
        let d = ( (fromCurrency.nominalDouble! * fromCurrency.valueDouble!) / (toCurrency.nominalDouble! * toCurrency.valueDouble!) ) * amount!
        
        return String(d)
    }
    
    var pathForXML: String {
        let path = NSSearchPathForDirectoriesInDomains(FileManager.SearchPathDirectory.libraryDirectory, FileManager.SearchPathDomainMask.userDomainMask, true)[0]+"/data.xml"
        
        if FileManager.default.fileExists(atPath: path) {
            return path
        }
        return Bundle.main.path(forResource: "data", ofType: "xml")!
        
    }
    
    var urlForXML: URL {
        return URL(fileURLWithPath: pathForXML)
    }
    
    //загрузка XML и сохранение его в каталоге приложения
    func loadXMLFile(date: Date?) {

        var strUrl = "http://www.cbr.ru/scripts/XML_daily.asp?date_req="
        
        if date != nil {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "dd/MM/yyyy"
            strUrl = strUrl + dateFormatter.string(from: date!)
        }
        
        let url = URL(string: strUrl)
        
        let task = URLSession.shared.dataTask(with: url!) { (data, responce, error) in
            
            var errorGlobal: String?
            
            if error == nil {
                let path = NSSearchPathForDirectoriesInDomains(FileManager.SearchPathDirectory.libraryDirectory, FileManager.SearchPathDomainMask.userDomainMask, true)[0]+"/data.xml"
                let urlForSave = URL(fileURLWithPath: path)
                do {
                    try data?.write(to: urlForSave)
                    print("Файл загружен")
                    self.parseXML()
                } catch {
                    print("Не удалось сохранить файл:\(error.localizedDescription)")
                    errorGlobal = error.localizedDescription
                }
                
            }else {
                print("Не удалось загрузить XML файл" + error!.localizedDescription)
                errorGlobal = error?.localizedDescription
            }
            
            if let errorGlobal = errorGlobal {
                NotificationCenter.default.post(name: NSNotification.Name("ErrorWhenXMLLoading"), object: self, userInfo: ["ErrorName":errorGlobal])
            }
        }
        
        NotificationCenter.default.post(name: NSNotification.Name(rawValue: "startLoadingXML"), object: self)
        task.resume()
        
    }
    
    //распарсить XML и положить его в currencies: [Currency], отправить уведомление приложению, что данные обновились
    func parseXML() {
        currencies = [Currency.rouble()]
        
        let parser  = XMLParser(contentsOf: urlForXML)
        parser?.delegate = self
        parser?.parse()
        
        NotificationCenter.default.post(name: NSNotification.Name(rawValue: "dataRefreshed"), object: self)
        
        for c in currencies {
            if c.charCode == fromCurrency.charCode {
                fromCurrency = c
            }
            if c.charCode == toCurrency.charCode {
                toCurrency = c
            }
        }
    }
    
    var currentCurrency: Currency?
    
    func parser(_ parser: XMLParser, didStartElement elementName: String, namespaceURI: String?, qualifiedName qName: String?, attributes attributeDict: [String : String] = [:]) {
        
        if elementName == "ValCurs" {
            if let currentDateString = attributeDict["Date"] {
                currentDate = currentDateString
            }
        }
        if elementName == "Valute" {
            currentCurrency = Currency()
        }
    }
    
    var currentCharacters: String = ""
    
    func parser(_ parser: XMLParser, foundCharacters string: String) {
        currentCharacters = string
    }
    
    func parser(_ parser: XMLParser, didEndElement elementName: String, namespaceURI: String?, qualifiedName qName: String?) {
        
        if elementName == "NumCode" {
            currentCurrency?.numCode = currentCharacters
        }
        if elementName == "CharCode" {
            currentCurrency?.charCode = currentCharacters
        }
        if elementName == "Nominal" {
            currentCurrency?.nominal = currentCharacters
            currentCurrency?.nominalDouble = Double(currentCharacters.replacingOccurrences(of: ",", with: "."))
        }
        if elementName == "Name" {
            currentCurrency?.name = currentCharacters
        }
        if elementName == "Value" {
            currentCurrency?.value = currentCharacters
            currentCurrency?.valueDouble = Double(currentCharacters.replacingOccurrences(of: ",", with: "."))
        }
        
        if elementName == "Valute" {
            currencies.append(currentCurrency!)
        }
    }
    
    
}
