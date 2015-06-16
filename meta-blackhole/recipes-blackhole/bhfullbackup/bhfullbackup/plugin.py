from Components.ActionMap import ActionMap
from Components.Label import Label
from Plugins.Plugin import PluginDescriptor
from Screens.MessageBox import MessageBox
from Screens.Console import Console
from Screens.Screen import Screen
from Tools.Directories import fileExists
from Components.ConfigList import ConfigListScreen
from Components.config import getConfigListEntry, config, ConfigYesNo, ConfigSelection, NoSave


class Bh_Bfull_main(Screen, ConfigListScreen):
	skin = """
	<screen position="center,center" size="902,340" title="Black Hole Full Backup">
		<widget name="config" position="30,10" size="840,60" scrollbarMode="showOnDemand"/>
		<widget name="lab1" position="30,70" size="840,60" font="Regular;24" valign="center" transparent="1"/>
		<ePixmap pixmap="skin_default/buttons/red.png" position="200,290" size="140,40" alphatest="on"/>
		<ePixmap pixmap="skin_default/buttons/green.png" position="550,290" size="140,40" alphatest="on"/>
		<widget name="key_red" position="200,290" zPosition="1" size="140,40" font="Regular;20" halign="center" valign="center" backgroundColor="#9f1313" transparent="1"/>
		<widget name="key_green" position="550,290" zPosition="1" size="140,40" font="Regular;20" halign="center" valign="center" backgroundColor="#1f771f" transparent="1"/>
	</screen>"""


	def __init__(self, session):
		Screen.__init__(self, session)
		
		self.list = []
		ConfigListScreen.__init__(self, self.list)
		self["key_red"] = Label(_("Backup"))
		self["key_green"] = Label(_("Cancel"))
		self["lab1"] = Label("")
		
		self["actions"] = ActionMap(["WizardActions", "ColorActions"],
		{
			"red": self.doBackUp,
			"green": self.close,
			"back": self.close

		})		
		self.updateList()
		self.deviceok = True
	
	def updateList(self):
		myusb = myusb1 = myhdd = ""
		myoptions =[]
		if fileExists("/proc/mounts"):
			f = open("/proc/mounts",'r')
 			for line in f.readlines():
				if line.find('/media/usb') != -1:
					myusb = "/media/usb/"
				elif line.find('/hdd') != -1:
					myhdd = "/media/hdd/" 
			f.close()
		
		
		if (myusb):
			myoptions.append((myusb,myusb))
		if (myhdd):
			myoptions.append((myhdd,myhdd))
		
		self.list = []
		self.mybk_path = NoSave(ConfigSelection(choices = myoptions))
		
		bk_path = getConfigListEntry(_("Path to store Full Backup"), self.mybk_path)
		if len(myoptions) > 0:
			self.list.append(bk_path)
			self["config"].list = self.list
			self["config"].l.setList(self.list)
		else:
			self["lab1"].setText(_("Sorry no device found to store backup. Please check your media in Black Hole devices panel."))
			self.deviceok = False
			
	def doBackUp(self):
		if self.mybk_path.value:
			mytitle = _("Black Hole Full Backup on: ") + self.mybk_path.value
			cmd = "/usr/bin/bh_backup_full.sh " + self.mybk_path.value
			self.session.open(Console, title = mytitle ,cmdlist = [cmd], finishedCallback = self.myEnd)
		else:
			self.session.open(MessageBox, _("Sorry no device found to store backup. Please check your media in Black Hole devices panel."), MessageBox.TYPE_INFO)
			
	def myEnd(self):
		self.close()

def main(session, **kwargs):
	session.open(Bh_Bfull_main)	

def menu(menuid, **kwargs):
	if menuid == "bhbackup":
		return [(_("BlackHole Full Backup"), main, "BlackHoleFullBackup", 1)]
	return []

def Plugins(**kwargs):
	return PluginDescriptor(name="BlackHoleFullBackup", description=_("Black Hole full image backup"), where = PluginDescriptor.WHERE_MENU, fnc=menu)
