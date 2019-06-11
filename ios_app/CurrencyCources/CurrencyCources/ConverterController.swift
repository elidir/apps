//
//  ConverterController.swift
//  CurrencyCources
//
//  Created by Alexander on 29/03/2019.
//  Copyright © 2019 Alexander. All rights reserved.
//

import UIKit

class ConverterController: UIViewController {

    
    @IBOutlet weak var labelCoursesForDate: UILabel!
    @IBOutlet weak var buttonFrom: UIButton!
    @IBOutlet weak var buttonTo: UIButton!
    @IBOutlet weak var textFrom: UITextField!
    @IBOutlet weak var textTo: UITextField!
    
    @IBAction func pushFromAction(_ sender: AnyObject) {
        let nc = storyboard?.instantiateViewController(withIdentifier: "selectedCurrencyNSID") as! UINavigationController
        (nc.viewControllers[0] as! SelectCurrencyController).flagCurrency = .from
        present(nc, animated: true, completion: nil)
    }
    
    @IBAction func pushToAction(_ sender: AnyObject) {
        let nc = storyboard?.instantiateViewController(withIdentifier: "selectedCurrencyNSID") as! UINavigationController
        (nc.viewControllers[0] as! SelectCurrencyController).flagCurrency = .to
        present(nc, animated: true, completion: nil)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        textFrom.delegate = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        refreshButtons()
        textFromEditingChange(self)
        labelCoursesForDate.text = "Курсы за дату:  \(Model.shared.currentDate)"
        navigationItem.rightBarButtonItem = nil
    }
    
    @IBOutlet weak var buttonDone: UIBarButtonItem!
    @IBAction func pushDoneAction(_ sender: Any) {
        textFrom.resignFirstResponder()
        navigationItem.rightBarButtonItem = nil
    }
    
    @IBAction func textFromEditingChange(_ sender: Any) {
        let amount = Double(textFrom.text!)
        textTo.text = Model.shared.convert(amount: amount)
    }
    
    func refreshButtons() {
        buttonFrom.setTitle(Model.shared.fromCurrency.charCode, for: UIControl.State.normal)
        buttonTo.setTitle(Model.shared.toCurrency.charCode, for: UIControl.State.normal)
    }

}

extension ConverterController: UITextFieldDelegate {
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        navigationItem.rightBarButtonItem = buttonDone
        return true
    }
}
